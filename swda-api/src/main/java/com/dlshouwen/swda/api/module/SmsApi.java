package com.dlshouwen.swda.api.module;

import java.util.Map;

/**
 * sms api
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SmsApi {

	/**
	 * send
	 * @param mobile
	 * @param params
	 * @return is send success
	 */
	boolean send(String mobile, Map<String, String> params);

	/**
	 * send code
	 * @param mobile
	 * @param key
	 * @param value
	 * @return is send success
	 */
	boolean sendCode(String mobile, String key, String value);

	/**
	 * verify code
	 * @param mobile
	 * @param code
	 * @return is send success
	 */
	boolean verifyCode(String mobile, String code);

}
