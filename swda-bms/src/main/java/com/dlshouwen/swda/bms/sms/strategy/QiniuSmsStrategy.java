package com.dlshouwen.swda.bms.sms.strategy;

import com.dlshouwen.swda.bms.sms.entity.SmsPlatform;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;

import java.util.Map;

/**
 * aliyun sms strategy
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class QiniuSmsStrategy implements SmsStrategy {

	/** platform */
	private final SmsPlatform platform;

	/** manager */
	private final SmsManager manager;

	/**
	 * contructor
	 * @param platform
	 */
	public QiniuSmsStrategy(SmsPlatform platform) {
//		set platform
		this.platform = platform;
//		create auth
		Auth auth = Auth.create(platform.getAccessKey(), platform.getSecretKey());
//		new sms manager
		manager = new SmsManager(auth);
	}

	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	@Override
	public void send(String mobile, Map<String, String> params) {
		try {
//			send message
			Response response = manager.sendSingleMessage(platform.getTemplateId(), mobile, params);
//			if not success
			if (!response.isOK()) {
				throw new SwdaException(response.error);
			}
		} catch (Exception e) {
			throw new SwdaException(e.getMessage());
		}
	}
}
