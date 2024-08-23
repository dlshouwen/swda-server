package com.dlshouwen.swda.bms.sms.feign;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.dlshouwen.swda.api.feign.SmsFeign;
import com.dlshouwen.swda.bms.sms.cache.SmsCache;
import com.dlshouwen.swda.bms.sms.service.SmsService;

import java.util.HashMap;
import java.util.Map;

/**
 * sms feign impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@AllArgsConstructor
public class SmsFeignImpl implements SmsFeign {

	/** sms service */
	private final SmsService smsService;

	/** sms cache */
	private final SmsCache smsCache;
	
	/**
	 * send
	 * @param mobile
	 * @param params
	 * @return
	 */
	@Override
	public Boolean send(String mobile, Map<String, String> params) {
		return smsService.send(mobile, params);
	}

	/**
	 * send code
	 * @param mobile
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Boolean sendCode(String mobile, String key, String value) {
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
//		return result
		return result;
	}

}
