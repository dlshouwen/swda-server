package com.dlshouwen.swda.bms.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.auth.mobile.MobileAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdLogin;
import com.dlshouwen.swda.bms.auth.enums.LoginOperationEnum;
import com.dlshouwen.swda.bms.auth.service.ICaptchaService;
import com.dlshouwen.swda.bms.auth.service.ILoginService;
import com.dlshouwen.swda.bms.auth.service.IUserTokenService;
import com.dlshouwen.swda.bms.auth.vo.AccessTokenVO;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.MobileLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;
import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.system.service.IUserService;
import com.dlshouwen.swda.bms.system.vo.UserVO;
import com.dlshouwen.swda.bms.vo.*;
import com.dlshouwen.swda.core.common.dict.CallResult;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.security.cache.TokenCache;
import com.dlshouwen.swda.core.security.crypto.Sm2Utils;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * login service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
//		validate captcha
		boolean valid = captchaService.validate(login.getKey(), login.getCaptcha());
//		if error
		if (!valid) {
//			TODO SAVE LOGIN LOG
//			save login log
//			loginLogService.saveLoginLog(login.getUsername(), CallResult.FAILURE, LoginOperationEnum.CAPTCHA_FAIL.getValue());
//			throw exception
			throw new SwdaException("验证码错误");
		}
//		defined authentication
		Authentication authentication;
//		try catch
		try {
//			authenticate
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), Sm2Utils.decrypt(login.getPassword())));
		} catch (BadCredentialsException e) {
//			throw exception
			throw new SwdaException("用户名或密码错误");
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
	 * send code
	 * @param mobile
	 * @return is success
	 */
	@Override
	public boolean sendCode(String mobile) {
//		generate code
		String code = RandomUtil.randomNumbers(6);
//		get user
		UserVO user = userService.getUserByMobile(mobile);
//		if user is empty
		if (user == null) {
//			throw exception
			throw new SwdaException("手机号未注册");
		}
//		send code
		return smsApi.sendCode(mobile, "code", code);
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
		loginLogService.saveLoginLog(user.getUsername(), CallResult.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
	}

}
