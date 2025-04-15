package com.dlshouwen.swda.api;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.core.sms.cache.SmsCache;
import com.dlshouwen.swda.core.sms.service.SmsService;
import com.dlshouwen.swda.core.base.exception.SwdaException;

import java.util.HashMap;
import java.util.Map;

/**
 * sms api impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
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
//		get code
		String code = smsCache.getCode(mobile);
//		if has code
		if(!StringUtils.isEmpty(code)) {
//			error
			throw new SwdaException("发送太频繁，请稍后再试。");
		}
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
