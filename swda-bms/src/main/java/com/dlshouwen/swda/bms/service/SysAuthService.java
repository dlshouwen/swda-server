package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.bms.vo.*;

/**
 * auth service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysAuthService {

	/**
	 * login by account
	 * @param login
	 * @return user token vo
	 */
	SysUserTokenVO loginByAccount(SysAccountLoginVO login);

	/**
	 * login by mobile
	 * @param login
	 * @return user token vo
	 */
	SysUserTokenVO loginByMobile(SysMobileLoginVO login);

	/**
	 * login by third
	 * @param login
	 * @return user token vo
	 */
	SysUserTokenVO loginByThird(SysThirdCallbackVO login);

	/**
	 * send code
	 * @param mobile
	 * @return is success
	 */
	boolean sendCode(String mobile);

	/**
	 * get access token
	 * @param refreshToken
	 * @return access token vo
	 */
	AccessTokenVO getAccessToken(String refreshToken);

	/**
	 * logout
	 * @param accessToken
	 */
	void logout(String accessToken);

}
