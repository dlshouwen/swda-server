package com.dlshouwen.swda.bms.core.sms.strategy;

import cn.hutool.core.map.MapUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.JsonUtils;

import java.util.Map;

/**
 * aliyun sms strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class AliyunSmsStrategy implements SmsStrategy {
	
	/** client */
	private final Client client;
	
	/** platform */
	private final SmsPlatform platform;

	/**
	 * constructor
	 * @param platform
	 */
	public AliyunSmsStrategy(SmsPlatform platform) {
//		set platform
		this.platform = platform;
		try {
//			new config
			Config config = new Config();
			config.setAccessKeyId(platform.getAccessKey());
			config.setAccessKeySecret(platform.getSecretKey());
			config.endpoint = "dysmsapi.aliyuncs.com";
//			set client
			this.client = new Client(config);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	@Override
	public void send(String mobile, Map<String, String> params) {
//		set request
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(platform.getSignName());
		request.setTemplateCode(platform.getTemplateId());
		request.setPhoneNumbers(mobile);
//		set template param
		if (MapUtil.isNotEmpty(params)) {
			request.setTemplateParam(JsonUtils.toJsonString(params));
		}
		try {
//			send message
			SendSmsResponse response = client.sendSms(request);
//			send error
			if (!"ok".equalsIgnoreCase(response.getBody().getCode())) {
				throw new SwdaException(response.getBody().getMessage());
			}
		} catch (Exception e) {
			throw new SwdaException(e.getMessage());
		}
	}

}
