package com.dlshouwen.swda.bms.sms.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.sms.cache.SmsCache;
import com.dlshouwen.swda.sms.service.SmsService;

import java.util.HashMap;
import java.util.Map;

/**
 * sms api impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Component
@AllArgsConstructor
public class SmsApiImpl implements SmsApi {
	
	/** sms service */
	private final SmsService smsService;
	
	/** sms cache */
	private final SmsCache smsCache;

	/**
	 * send 
	 * @param mobile
	 * @param params
	 * @return result
	 */
	@Override
	public boolean send(String mobile, Map<String, String> params) {
		return smsService.send(mobile, params);
	}

	/**
	 * send code
	 * @param mobile
	 * @param key
	 * @param value
	 * @return result
	 */
	@Override
	public boolean sendCode(String mobile, String key, String value) {
//		set params
		Map<String, String> params = new HashMap<>();
		params.put(key, value);
//		send message
		boolean result = smsService.send(mobile, params);
//		if success
		if (result) {
//			save code
			smsCache.saveCode(mobile, value);
		}
//		return 
		return result;
	}

	/**
	 * verify code 
	 * @param mobile
	 * @param code
	 * @return result
	 */
	@Override
	public boolean verifyCode(String mobile, String code) {
//		get code
		String value = smsCache.getCode(mobile);
//		if not null
		if (value != null) {
//			delete code
			smsCache.deleteCode(mobile);
//			valid
			return value.equalsIgnoreCase(code);
		}
		return false;
	}

}
