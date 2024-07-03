package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.bms.vo.*;

/**
 * auth service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface ILoginService {

	/**
	 * login by account
	 * @param login
	 * @return user token vo
	 */
	UserTokenVO loginByAccount(UserLoginVO login);

	/**
	 * login by mobile
	 * @param login
	 * @return user token vo
	 */
	UserTokenVO loginByMobile(MobileLoginVO login);

	/**
	 * login by third
	 * @param login
	 * @return user token vo
	 */
	UserTokenVO loginByThird(AuthCallbackVO login);

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
