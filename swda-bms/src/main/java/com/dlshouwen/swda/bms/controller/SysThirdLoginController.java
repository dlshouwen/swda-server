package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.bms.service.SysThirdLoginService;
import com.dlshouwen.swda.bms.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.vo.AuthVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * third login
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/third")
@Tag(name = "third login")
@AllArgsConstructor
public class SysThirdLoginController {
	
	/** third login service */
	private final SysThirdLoginService sysThirdLoginService;
	
	/** third login config service */
	private final SysThirdLoginConfigService sysThirdLoginConfigService;

	/**
	 * list
	 * @return result
	 */
	@GetMapping("list")
	@Operation(name = "list")
	public R<List<AuthVO>> list() {
//		list by user id
		List<AuthVO> list = sysThirdLoginService.listByUserId(SecurityUser.getUserId());
//		return
		return R.ok(list);
	}

	/**
	 * render auth
	 * @param source
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("render/{source}")
	public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
//		get auth request
		AuthRequest authRequest = sysThirdLoginConfigService.getAuthRequest(source);
//		get authorize url
		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
//		redirect
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * login
	 * @param source
	 * @param callback
	 * @return result
	 */
	@RequestMapping("/callback/{source}")
	public ModelAndView login(@PathVariable("source") String source, AuthCallback callback) {
//		create info
		Map<String, Object> map = new HashMap<>();
//		set open type, state, code
		map.put("openType", source);
		map.put("state", callback.getState());
		map.put("code", callback.getCode());
//		return model and view
		return new ModelAndView("third_login", map);
	}

	/**
	 * bind
	 * @param thirdCallbackVO
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("bind")
	@Operation(name = "bind", type = OperateType.INSERT)
	public R<String> bind(@RequestBody AuthCallbackVO vo) {
//		get auth request
		AuthRequest authRequest = sysThirdLoginConfigService.getAuthRequest(vo.getOpenType());
//		get auth callback
		AuthCallback callback = AuthCallback.builder().code(vo.getCode()).state(vo.getState()).build();
//		do login
		AuthResponse<AuthUser> response = authRequest.login(callback);
//		if login not success
		if (!response.ok()) {
//			throw exception
			throw new RuntimeException("第三方登录失败");
		}
//		bind user
		sysThirdLoginService.bind(SecurityUser.getUserId(), vo.getOpenType(), response.getData());
//		return
		return R.ok();
	}

	/**
	 * unbind
	 * @param openType
	 * @return result
	 */
	@PutMapping("unbind/{openType}")
	@Operation(name = "unbind", type = OperateType.UPDATE)
	public R<String> unBind(@PathVariable("openType") String openType) {
//		unbind
		sysThirdLoginService.unBind(SecurityUser.getUserId(), openType);
//		return
		return R.ok();
	}

}
