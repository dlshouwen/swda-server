package com.dlshouwen.swda.bms.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.bms.auth.service.ICaptchaService;
import com.dlshouwen.swda.bms.auth.service.ILoginService;
import com.dlshouwen.swda.bms.auth.service.IUserTokenService;
import com.dlshouwen.swda.bms.auth.vo.AccessTokenVO;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.MobileLoginVO;
import com.dlshouwen.swda.bms.auth.vo.SendCodeVO;
import com.dlshouwen.swda.bms.auth.vo.UserLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;
import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.system.service.IUserService;
import com.dlshouwen.swda.bms.system.vo.UserVO;
import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.common.entity.Data;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.log.dict.LoginStatus;
import com.dlshouwen.swda.core.log.dict.LoginType;
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
		if (String.valueOf(ZeroOne.YES).equals(Data.attr.get("account_login_captcha_enable"))) {
//			validate captcha
			boolean valid = captchaService.validate(login.getKey(), login.getCaptcha());
//			if error
			if (!valid) {
//				save login log
				loginLogService.saveLoginLog(LoginType.ACCOUNT, LoginStatus.CAPTCHA_ERROR, login.getUsername(), "验证码错误");
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
	 * send code
	 * @param sendCode
	 * @return is success
	 */
	@Override
	public boolean sendCode(SendCodeVO sendCode) {
//		if captcha enabled
		if (String.valueOf(ZeroOne.YES).equals(Data.attr.get("sms_send_code_captcha_enable"))) {
//			validate captcha
			boolean valid = captchaService.validate(sendCode.getKey(), sendCode.getCaptcha());
//			if error
			if (!valid) {
//				save login log
				loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.CAPTCHA_ERROR, sendCode.getMobile(), "验证码错误");
//				throw exception
				throw new SwdaException("验证码错误");
			}
		}
//		get user
		UserVO user = userService.getUserByMobile(sendCode.getMobile());
//		if user is empty
		if (user == null) {
//			save login log
			loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.USER_NOR_FOUND, sendCode.getMobile(), "手机号未注册");
//			throw exception
			throw new SwdaException("手机号未注册");
		}
//		generate code
		String code = RandomUtil.randomNumbers(6);
//		send code
		return smsApi.sendCode(sendCode.getMobile(), "code", code);
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
//		save login log
//		loginLogService.saveLoginLog(user.getUsername(), CallResult.SUCCESS, LogoutType.NORMAL);
	}

}