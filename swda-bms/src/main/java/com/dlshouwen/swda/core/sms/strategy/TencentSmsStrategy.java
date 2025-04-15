package com.dlshouwen.swda.bms.core.sms.strategy;

import cn.hutool.core.map.MapUtil;

import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;

import java.util.Map;

/**
 * aliyun sms strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class TencentSmsStrategy implements SmsStrategy {
	
	/** client */
	private SmsClient client;

	/** platform */
	private final SmsPlatform platform;

	/**
	 * constructor
	 * @param platform
	 */
	public TencentSmsStrategy(SmsPlatform platform) {
//		set platform
		this.platform = platform;
		try {
//			set http profile
			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setReqMethod("POST");
			httpProfile.setEndpoint("sms.tencentcloudapi.com");
//			set client profile
			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);
//			set client
			Credential cred = new Credential(platform.getAccessKey(), platform.getSecretKey());
			this.client = new SmsClient(cred, "ap-guangzhou", clientProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	@Override
	public void send(String mobile, Map<String, String> params) {
//		set send request info
		SendSmsRequest request = new SendSmsRequest();
		request.setSmsSdkAppId(platform.getAppId());
		request.setSignName(platform.getSignName());
		request.setTemplateId(platform.getTemplateId());
//		set template params
		if (MapUtil.isNotEmpty(params)) {
			request.setTemplateParamSet(params.values().toArray(new String[0]));
		}
//		set phone number
		String[] phoneNumberSet = { "+86" + mobile };
		request.setPhoneNumberSet(phoneNumberSet);
//		set sender id ( null if china )
		request.setSenderId(platform.getSenderId());
		try {
//			send message
			SendSmsResponse response = client.SendSms(request);
//			get send status
			SendStatus sendStatus = response.getSendStatusSet()[0];
//			if failure
			if (!"ok".equalsIgnoreCase(sendStatus.getCode())) {
				throw new SwdaException(sendStatus.getMessage());
			}
		} catch (Exception e) {
			throw new SwdaException(e.getMessage());
		}
	}

}
