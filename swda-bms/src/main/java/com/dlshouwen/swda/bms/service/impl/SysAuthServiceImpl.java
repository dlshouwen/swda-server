package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.auth.mobile.MobileAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdLogin;
import com.dlshouwen.swda.bms.enums.LoginOperationEnum;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.*;
import com.dlshouwen.swda.core.cache.TokenCache;
import com.dlshouwen.swda.core.dict.CallResult;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.utils.Sm2Utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * auth service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
	
	/** captcha service */
	private final SysCaptchaService sysCaptchaService;
	
	/** token store cache */
	private final TokenCache tokenStoreCache;
	
	/** authentication manager */
	private final AuthenticationManager authenticationManager;
	
	/** login log service */
	private final SysLogLoginService sysLogLoginService;
	
	/** user service */
	private final SysUserService sysUserService;
	
	/** user token service */
	private final SysUserTokenService sysUserTokenService;
	
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
		boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
//		if error
		if (!flag) {
//			save login log
			sysLogLoginService.save(login.getUsername(), CallResult.FAILURE, LoginOperationEnum.CAPTCHA_FAIL.getValue());
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
		UserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());
//		save user
		tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);
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
		UserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());
//		save user
		tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);
//		return user token vo
		return userTokenVO;
	}

	/**
	 * login by third
	 * @param thirdCallbackVO
	 * @return user token vo
	 */
	@Override
	public UserTokenVO loginByThird(AuthCallbackVO login) {
//		defined authentication
		Authentication authentication;
		try {
//			convert to third login
			ThirdLogin thirdLogin = BeanUtil.copyProperties(login, ThirdLogin.class);
//			authenticate
			authentication = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
		} catch (BadCredentialsException e) {
//			throw exception
			throw new SwdaException("第三方登录失败");
		}
//		get principal
		UserDetail user = (UserDetail) authentication.getPrincipal();
//		create token
		UserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());
//		save user
		tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);
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
		UserVO user = sysUserService.getByMobile(mobile);
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
		UserTokenVO token = sysUserTokenService.refreshToken(refreshToken);
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
		UserDetail user = tokenStoreCache.getUser(accessToken);
//		delete user
		tokenStoreCache.deleteUser(accessToken);
//		expire token
		sysUserTokenService.expireToken(user.getUserId());
//		save login log
		sysLogLoginService.save(user.getUsername(), CallResult.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
	}

}
