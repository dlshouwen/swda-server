package com.dlshouwen.swda.core.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.core.base.service.ICaptchaService;
import com.dlshouwen.swda.core.base.service.ILoginService;
import com.dlshouwen.swda.core.base.service.IUserTokenService;
import com.dlshouwen.swda.core.base.vo.AccessTokenVO;
import com.dlshouwen.swda.core.base.vo.AuthCallbackVO;
import com.dlshouwen.swda.core.base.vo.MobileLoginVO;
import com.dlshouwen.swda.core.base.vo.MobileSendCodeVO;
import com.dlshouwen.swda.core.base.vo.UserLoginVO;
import com.dlshouwen.swda.core.base.vo.UserTokenVO;
import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.permission.service.IUserService;
import com.dlshouwen.swda.bms.permission.vo.UserVO;
import com.dlshouwen.swda.core.base.dict.ZeroOne;
import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.log.dict.LoginStatus;
import com.dlshouwen.swda.core.log.dict.LoginType;
import com.dlshouwen.swda.core.log.dict.LogoutType;
import com.dlshouwen.swda.core.security.cache.TokenCache;
import com.dlshouwen.swda.core.security.crypto.Sm2Utils;
import com.dlshouwen.swda.core.security.mobile.MobileAuthenticationToken;
import com.dlshouwen.swda.core.security.third.ThirdAuthenticationToken;
import com.dlshouwen.swda.core.security.third.ThirdLogin;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * login service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl implements ILoginService {
	
	/** captcha service */
	private final ICaptchaService captchaService;
	
	/** token store cache */
	private final TokenCache tokenCache;
	
	/** authentication manager */
	private final AuthenticationManager authenticationManager;
	
	/** login log service */
	private final ILoginLogService loginLogService;
	
	/** user service */
	private final IUserService userService;
	
	/** user token service */
	private final IUserTokenService userTokenService;
	
	/** sms api */
	private final SmsApi smsApi;

	/**
	 * login by account
	 * @param accountLoginVO
	 * @return user token vo
	 */
	@Override
	public UserTokenVO loginByAccount(UserLoginVO login) {
//		if captcha enabled
		if (String.valueOf(ZeroOne.YES).equals(Data.attr.get("account_login_captcha_enabled"))) {
//			validate captcha
			boolean valid = captchaService.validate(login.getKey(), login.getCaptcha());
//			if error
			if (!valid) {
//				save login log
				loginLogService.saveLoginLog(LoginType.ACCOUNT, LoginStatus.CAPTCHA_ERROR, login.getUsername(), "验证码错误", null);
//				throw exception
				throw new SwdaException("验证码错误");
			}
		}
//		defined authentication
		Authentication authentication;
//		try catch
		try {
//			authenticate
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), Sm2Utils.decrypt(login.getPassword())));
		} catch (BadCredentialsException | UsernameNotFoundException e) {
//			throw exception
			throw new SwdaException("用户名或密码错误");
		} catch (CredentialsExpiredException e) {
//			throw exception
			throw new SwdaException("您的认证信息已过期");			
		} catch (DisabledException e) {
//			throw exception
			throw new SwdaException("您的账号已被禁用");
		}
//		get principal
		UserDetail user = (UserDetail) authentication.getPrincipal();
//		create token
		UserTokenVO userTokenVO = userTokenService.createToken(user.getUserId());
//		save user
		tokenCache.saveUser(userTokenVO.getAccessToken(), user);
//		return user token vo
		return userTokenVO;
	}

	/**
	 * mobile send code
	 * @param mobileSendCode
	 * @return is success
	 */
	@Override
	public boolean mobileSendCode(MobileSendCodeVO mobileSendCode) {
//		if captcha enabled
		if (String.valueOf(ZeroOne.YES).equals(Data.attr.get("login_mobile_sms_send_code_captcha_enabled"))) {
//			validate captcha
			boolean valid = captchaService.validate(mobileSendCode.getKey(), mobileSendCode.getCaptcha());
//			if error
			if (!valid) {
//				save login log
				loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.CAPTCHA_ERROR, mobileSendCode.getMobile(), "验证码错误", null);
//				throw exception
				throw new SwdaException("验证码错误");
			}
		}
//		get user
		UserVO user = userService.getUserByMobile(mobileSendCode.getMobile());
//		if user is empty
		if (user == null) {
//			save login log
			loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.USER_NOR_FOUND, mobileSendCode.getMobile(), "手机号未注册", null);
//			throw exception
			throw new SwdaException("手机号未注册");
		}
//		generate code
		String code = RandomUtil.randomNumbers(6);
//		send code
		return smsApi.sendCode(mobileSendCode.getMobile(), "code", code);
	}

	/**
	 * login by mobile
	 * @param mobileLoginVO
	 * @return user token vo
	 */
	@Override
	public UserTokenVO loginByMobile(MobileLoginVO login) {
//		defined authentication
		Authentication authentication;
//		try catch
		try {
//			authenticate
			authentication = authenticationManager.authenticate(new MobileAuthenticationToken(login.getMobile(), login.getCode()));
		} catch (BadCredentialsException e) {
//			throw exception
			throw new SwdaException("手机号或验证码错误");
		}
//		get principal
		UserDetail user = (UserDetail) authentication.getPrincipal();
//		create token
		UserTokenVO userTokenVO = userTokenService.createToken(user.getUserId());
//		save user
		tokenCache.saveUser(userTokenVO.getAccessToken(), user);
//		return user token vo
		return userTokenVO;
	}

	/**
	 * login by auth
	 * @param callback
	 * @return user token vo
	 */
	@Override
	public UserTokenVO loginByAuth(AuthCallbackVO callback) {
//		defined authentication
		Authentication authentication;
		try {
//			convert to third login
			ThirdLogin thirdLogin = BeanUtil.copyProperties(callback, ThirdLogin.class);
//			authenticate
			authentication = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
		} catch (BadCredentialsException e) {
//			throw exception
			throw new SwdaException("第三方登录失败");
		}
//		get principal
		UserDetail user = (UserDetail) authentication.getPrincipal();
//		create token
		UserTokenVO userTokenVO = userTokenService.createToken(user.getUserId());
//		save user
		tokenCache.saveUser(userTokenVO.getAccessToken(), user);
//		return user token vo
		return userTokenVO;
	}

	/**
	 * get access token
	 * @param refreshToken
	 * @return access token vo
	 */
	@Override
	public AccessTokenVO getAccessToken(String refreshToken) {
//		get user token
		UserTokenVO token = userTokenService.refreshToken(refreshToken);
//		create access token
		AccessTokenVO accessToken = new AccessTokenVO();
//		set access token, access token expire
		accessToken.setAccessToken(token.getAccessToken());
		accessToken.setAccessTokenExpire(token.getAccessTokenExpire());
//		return access token
		return accessToken;
	}

	/**
	 * logout
	 * @param accessToken
	 */
	@Override
	public void logout(String accessToken) {
//		get user
		UserDetail user = tokenCache.getUser(accessToken);
//		delete user
		tokenCache.deleteUser(accessToken);
//		expire token
		userTokenService.expireToken(user.getUserId());
//		save logout log
		loginLogService.saveLogoutLog(LogoutType.NORMAL);
	}

}
