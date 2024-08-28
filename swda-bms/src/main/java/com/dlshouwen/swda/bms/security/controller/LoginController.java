package com.dlshouwen.swda.bms.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.auth.vo.AccessTokenVO;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.CaptchaVO;
import com.dlshouwen.swda.bms.auth.vo.MobileLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;
import com.dlshouwen.swda.bms.security.service.ICaptchaService;
import com.dlshouwen.swda.bms.security.service.ILoginService;
import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.utils.TokenUtils;

import org.springframework.web.bind.annotation.*;

/**
 * login
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
@Tag(name = "login")
@AllArgsConstructor
public class LoginController {
	
	/** captcha service */
	private final ICaptchaService captchaService;
	
	/** login service */
	private final ILoginService loginService;

	/**
	 * captcha
	 * @return result
	 */
	@GetMapping("/captcha")
	@Operation(name = "captcha", type = OperateType.SEARCH)
	public R<CaptchaVO> captcha() {
//		generate captcha vo
		CaptchaVO captchaVO = captchaService.generate();
//		return
		return R.ok(captchaVO);
	}

	/**
	 * login by account
	 * @param loginVO
	 * @return result
	 */
	@PostMapping("/account")
	@Operation(name = "account", type = OperateType.LOGIN)
	public R<UserTokenVO> loginByAccount(@RequestBody UserLoginVO login) {
//		login by account
		UserTokenVO token = loginService.loginByAccount(login);
//		return
		return R.ok(token);
	}

	/**
	 * send code
	 * @param mobile
	 * @return result
	 */
	@PostMapping("/send/code")
	@Operation(name = "send code", type = OperateType.SEARCH)
	public R<String> sendCode(String mobile) {
//		send code
		boolean flag = loginService.sendCode(mobile);
//		if not success
		if (!flag) {
//			return
			return R.error("短信发送失败！");
		}
//		return
		return R.ok();
	}

	/**
	 * mobile
	 * @param mobileLoginVO
	 * @return result
	 */
	@PostMapping("/mobile")
	@Operation(name = "login by mobile", type = OperateType.LOGIN)
	public R<UserTokenVO> loginByMobile(@RequestBody MobileLoginVO login) {
//		login by mobile
		UserTokenVO token = loginService.loginByMobile(login);
//		return
		return R.ok(token);
	}

	/**
	 * auth
	 * @param thirdLoginVO
	 * @return result
	 */
	@PostMapping("/auth")
	@Operation(name = "login by auth", type = OperateType.LOGIN)
	public R<UserTokenVO> loginByAuth(@RequestBody AuthCallbackVO callback) {
//		login by auth
		UserTokenVO token = loginService.loginByAuth(callback);
//		return
		return R.ok(token);
	}

	/**
	 * get access token
	 * @param refreshToken
	 * @return result
	 */
	@PostMapping("/token")
	@Operation(name = "get access token", type = OperateType.SEARCH)
	public R<AccessTokenVO> token(String refreshToken) {
//		get access token
		AccessTokenVO token = loginService.getAccessToken(refreshToken);
//		return
		return R.ok(token);
	}

	/**
	 * logout
	 * @param request
	 * @return result
	 */
	@PostMapping("/logout")
	@Operation(name = "logout", type = OperateType.LOGOUT)
	public R<String> logout(HttpServletRequest request) {
//		logout
		loginService.logout(TokenUtils.getAccessToken(request));
//		return
		return R.ok();
	}

}
