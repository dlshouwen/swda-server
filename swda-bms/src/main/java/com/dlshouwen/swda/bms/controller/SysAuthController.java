package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.service.SysAuthService;
import com.dlshouwen.swda.bms.service.SysCaptchaService;
import com.dlshouwen.swda.bms.vo.*;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.utils.TokenUtils;

import org.springframework.web.bind.annotation.*;

/**
 * auth
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "auth")
@AllArgsConstructor
public class SysAuthController {
	
	/** captcha service */
	private final SysCaptchaService sysCaptchaService;
	
	/** auth service */
	private final SysAuthService sysAuthService;

	/**
	 * captcha
	 * @return result
	 */
	@GetMapping("captcha")
	@Operation(name = "captcha")
	public R<CaptchaVO> captcha() {
//		generate captcha vo
		CaptchaVO captchaVO = sysCaptchaService.generate();
//		return
		return R.ok(captchaVO);
	}

	/**
	 * captcha enabled
	 * @return result
	 */
	@GetMapping("captcha/enabled")
	@Operation(name = "is enabled captcha")
	public R<Boolean> captchaEnabled() {
//		is captcha enbaled
		boolean enabled = sysCaptchaService.isCaptchaEnabled();
//		return
		return R.ok(enabled);
	}

	/**
	 * login
	 * @param loginVO
	 * @return result
	 */
	@PostMapping("login")
	@Operation(name = "login")
	public R<UserTokenVO> login(@RequestBody AccountLoginVO login) {
//		login by account
		UserTokenVO token = sysAuthService.loginByAccount(login);
//		return
		return R.ok(token);
	}

	/**
	 * send code
	 * @param mobile
	 * @return result
	 */
	@PostMapping("send/code")
	@Operation(name = "send code")
	public R<String> sendCode(String mobile) {
//		send code
		boolean flag = sysAuthService.sendCode(mobile);
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
	@PostMapping("mobile")
	@Operation(name = "mobile")
	public R<UserTokenVO> mobile(@RequestBody MobileLoginVO login) {
//		login by mobile
		UserTokenVO token = sysAuthService.loginByMobile(login);
//		return
		return R.ok(token);
	}

	/**
	 * third
	 * @param thirdLoginVO
	 * @return result
	 */
	@PostMapping("third")
	@Operation(name = "third login")
	public R<UserTokenVO> third(@RequestBody AuthCallbackVO login) {
//		login by third
		UserTokenVO token = sysAuthService.loginByThird(login);
//		return
		return R.ok(token);
	}

	/**
	 * get access token
	 * @param refreshToken
	 * @return result
	 */
	@PostMapping("token")
	@Operation(name = "get access token")
	public R<AccessTokenVO> token(String refreshToken) {
//		get access token
		AccessTokenVO token = sysAuthService.getAccessToken(refreshToken);
//		return
		return R.ok(token);
	}

	/**
	 * logout
	 * @param request
	 * @return result
	 */
	@PostMapping("logout")
	@Operation(name = "logout")
	public R<String> logout(HttpServletRequest request) {
//		logout
		sysAuthService.logout(TokenUtils.getAccessToken(request));
//		return
		return R.ok();
	}

}
