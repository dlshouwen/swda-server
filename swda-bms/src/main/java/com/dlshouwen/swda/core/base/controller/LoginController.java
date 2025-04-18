package com.dlshouwen.swda.core.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.service.ICaptchaService;
import com.dlshouwen.swda.core.base.service.ILoginService;
import com.dlshouwen.swda.core.base.vo.AccessTokenVO;
import com.dlshouwen.swda.core.base.vo.AuthCallbackVO;
import com.dlshouwen.swda.core.base.vo.CaptchaVO;
import com.dlshouwen.swda.core.base.vo.MobileLoginVO;
import com.dlshouwen.swda.core.base.vo.MobileSendCodeVO;
import com.dlshouwen.swda.core.base.vo.UserLoginVO;
import com.dlshouwen.swda.core.base.vo.UserTokenVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.utils.TokenUtils;

import org.springframework.web.bind.annotation.*;

/**
 * login
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("")
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
	@GetMapping("/login/captcha")
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
	@PostMapping("/login/account")
	@Operation(name = "account", type = OperateType.LOGIN)
	public R<UserTokenVO> loginByAccount(@RequestBody UserLoginVO login) {
//		login by account
		UserTokenVO token = loginService.loginByAccount(login);
//		return
		return R.ok(token);
	}

	/**
	 * mobile send code
	 * @param mobileSendCode
	 * @return result
	 */
	@PostMapping("/login/mobile/send/code")
	@Operation(name = "mobile send code", type = OperateType.SEARCH)
	public R<String> mobileSendCode(@RequestBody MobileSendCodeVO mobileSendCode) {
//		mobile send code
		boolean result = loginService.mobileSendCode(mobileSendCode);
//		if not success
		if (!result) {
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
	@PostMapping("/login/mobile")
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
	@PostMapping("/login/auth")
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
	@PostMapping("/login/token")
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
	@PostMapping("/sign-out")
	@Operation(name = "logout", type = OperateType.LOGOUT)
	public R<String> logout(HttpServletRequest request) {
//		logout
		loginService.logout(TokenUtils.getAccessToken(request));
//		return
		return R.ok();
	}

}
