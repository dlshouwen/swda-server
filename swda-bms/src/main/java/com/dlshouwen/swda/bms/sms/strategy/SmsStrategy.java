package com.dlshouwen.swda.bms.sms.strategy;

import java.util.Map;

/**
 * sms strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface SmsStrategy {
	
	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	void send(String mobile, Map<String, String> params);

}
