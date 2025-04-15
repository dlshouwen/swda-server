package com.dlshouwen.swda.core.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.base.service.IAuthService;
import com.dlshouwen.swda.core.base.vo.AuthCallbackVO;
import com.dlshouwen.swda.core.base.vo.AuthVO;
import com.dlshouwen.swda.bms.platform.service.IAuthPlatformService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auth
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "auth")
@AllArgsConstructor
public class AuthController {
	
	/** auth service */
	private final IAuthService authService;
	
	/** auth platform service */
	private final IAuthPlatformService authPlatformService;

	/**
	 * get auth list
	 * @return auth list
	 */
	@GetMapping("/list")
	@Operation(name = "get auth list", type = OperateType.SEARCH)
	public R<List<AuthVO>> getAuthList() {
//		get auth list
		List<AuthVO> authList = authService.getAuthList(SecurityUser.getUserId());
//		return
		return R.ok(authList);
	}

	/**
	 * render auth
	 * @param openType
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/render/{openType}")
	public void renderAuth(@PathVariable("openType") Integer openType, HttpServletResponse response) throws IOException {
//		get auth request
		AuthRequest authRequest = authPlatformService.getAuthRequest(openType);
//		get authorize url
		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
//		redirect
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * callback auth
	 * @param openType
	 * @param callback
	 * @return result
	 */
	@RequestMapping("/callback/{openType}")
	public ModelAndView callbackAuth(@PathVariable("openType") Integer openType, AuthCallback callback) {
//		create data
		Map<String, Object> data = new HashMap<>();
//		set open type, state, code
		data.put("openType", openType);
		data.put("state", callback.getState());
		data.put("code", callback.getCode());
//		return model and view
		return new ModelAndView("auth", data);
	}

	/**
	 * bind auth
	 * @param thirdCallbackVO
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/bind")
	@Operation(name = "bind", type = OperateType.INSERT)
	public R<String> bindAuth(@RequestBody AuthCallbackVO authCallbackVO) {
//		get auth request
		AuthRequest authRequest = authPlatformService.getAuthRequest(authCallbackVO.getOpenType());
//		get auth callback
		AuthCallback callback = AuthCallback.builder().code(authCallbackVO.getCode()).state(authCallbackVO.getState()).build();
//		do login
		AuthResponse<AuthUser> response = authRequest.login(callback);
//		if login not success
		if (!response.ok()) {
//			throw exception
			throw new RuntimeException("第三方登录失败");
		}
//		bind auth
		authService.bindAuth(SecurityUser.getUserId(), authCallbackVO.getOpenType(), response.getData());
//		return
		return R.ok();
	}

	/**
	 * unbind auth
	 * @param openType
	 * @return result
	 */
	@PutMapping("/unbind/{openType}")
	@Operation(name = "unbind", type = OperateType.UPDATE)
	public R<String> unbindAuth(@PathVariable("openType") Integer openType) {
//		unbind
		authService.unbindAuth(SecurityUser.getUserId(), openType);
//		return
		return R.ok();
	}

}
