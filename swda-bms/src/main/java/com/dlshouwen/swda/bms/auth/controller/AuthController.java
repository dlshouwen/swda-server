package com.dlshouwen.swda.bms.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.bms.auth.service.IAuthPlatformService;
import com.dlshouwen.swda.bms.auth.service.IAuthService;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.AuthVO;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auth
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
	 * @param authPlatformType
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/render/{authPlatformType}")
	public void renderAuth(@PathVariable("authPlatformType") Integer authPlatformType, HttpServletResponse response) throws IOException {
//		get auth request
		AuthRequest authRequest = authPlatformService.getAuthRequest(authPlatformType);
//		get authorize url
		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
//		redirect
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * login
	 * @param authPlatformType
	 * @param callback
	 * @return result
	 */
	@RequestMapping("/callback/{authPlatformType}")
	public ModelAndView login(@PathVariable("authPlatformType") Integer authPlatformType, AuthCallback callback) {
//		create data
		Map<String, Object> data = new HashMap<>();
//		set open type, state, code
		data.put("authPlatformType", authPlatformType);
		data.put("state", callback.getState());
		data.put("code", callback.getCode());
//		return model and view
		return new ModelAndView("auth", data);
	}

	/**
	 * bind
	 * @param thirdCallbackVO
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/bind")
	@Operation(name = "bind", type = OperateType.INSERT)
	public R<String> bind(@RequestBody AuthCallbackVO authCallbackVO) {
//		get auth request
		AuthRequest authRequest = authPlatformService.getAuthRequest(authCallbackVO.getAuthPlatformType());
//		get auth callback
		AuthCallback callback = AuthCallback.builder().code(authCallbackVO.getCode()).state(authCallbackVO.getState()).build();
//		do login
		AuthResponse<AuthUser> response = authRequest.login(callback);
//		if login not success
		if (!response.ok()) {
//			throw exception
			throw new RuntimeException("第三方登录失败");
		}
//		bind user
		authService.bind(SecurityUser.getUserId(), authCallbackVO.getAuthPlatformType(), response.getData());
//		return
		return R.ok();
	}

	/**
	 * unbind
	 * @param authPlatformType
	 * @return result
	 */
	@PutMapping("/unbind/{authPlatformType}")
	@Operation(name = "unbind", type = OperateType.UPDATE)
	public R<String> unBind(@PathVariable("authPlatformType") Integer authPlatformType) {
//		unbind
		authService.unBind(SecurityUser.getUserId(), authPlatformType);
//		return
		return R.ok();
	}

}
