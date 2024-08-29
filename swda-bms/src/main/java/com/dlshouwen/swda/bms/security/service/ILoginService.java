package com.dlshouwen.swda.bms.security.service;

import com.dlshouwen.swda.bms.auth.vo.AccessTokenVO;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;
import com.dlshouwen.swda.bms.security.vo.MobileLoginVO;
import com.dlshouwen.swda.bms.security.vo.SendCodeVO;
import com.dlshouwen.swda.bms.security.vo.UserLoginVO;

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
	 * send code
	 * @param sendCode
	 * @return is success
	 */
	boolean sendCode(SendCodeVO sendCode);

	/**
	 * login by mobile
	 * @param login
	 * @return user token vo
	 */
	UserTokenVO loginByMobile(MobileLoginVO login);

	/**
	 * login by auth
	 * @param callback
	 * @return user token vo
	 */
	UserTokenVO loginByAuth(AuthCallbackVO callback);

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
