package com.dlshouwen.swda.auth.service;

/**
 * mobile verify code service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface MobileVerifyCodeService {

	/**
	 * verify code
	 * @param mobile
	 * @param code
	 * @return is success
	 */
	boolean verifyCode(String mobile, String code);

}
