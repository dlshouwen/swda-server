package com.dlshouwen.swda.bms.auth.service;

import com.dlshouwen.swda.bms.auth.vo.AccessTokenVO;
import com.dlshouwen.swda.bms.auth.vo.AuthCallbackVO;
import com.dlshouwen.swda.bms.auth.vo.MobileLoginVO;
import com.dlshouwen.swda.bms.auth.vo.MobileSendCodeVO;
import com.dlshouwen.swda.bms.auth.vo.UserLoginVO;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;

/**
 * auth service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ILoginService {

	/**
	 * login by account
	 * @param login
	 * @return user token vo
	 */
	UserTokenVO loginByAccount(UserLoginVO login);

	/**
	 * mobile send code
	 * @param mobileSendCode
	 * @return is success
	 */
	boolean mobileSendCode(MobileSendCodeVO mobileSendCode);

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
