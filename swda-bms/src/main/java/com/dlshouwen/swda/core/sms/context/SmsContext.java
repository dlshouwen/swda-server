package com.dlshouwen.swda.core.sms.context;

import java.util.Map;

import com.dlshouwen.swda.core.sms.strategy.AliyunSmsStrategy;
import com.dlshouwen.swda.core.sms.strategy.HuaweiSmsStrategy;
import com.dlshouwen.swda.core.sms.strategy.QiniuSmsStrategy;
import com.dlshouwen.swda.core.sms.strategy.SmsStrategy;
import com.dlshouwen.swda.core.sms.strategy.TencentSmsStrategy;
import com.dlshouwen.swda.bms.platform.dict.SmsPlatformType;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.core.base.exception.SwdaException;

/**
 * sms context
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class SmsContext {
	
	/** sms strategy */
	private final SmsStrategy smsStrategy;

	/**
	 * constructor
	 * @param platform
	 */
	public SmsContext(SmsPlatform platform) {
//		instance sms strategy by type
		if (platform.getSmsPlatformType() == SmsPlatformType.ALIYUN) {
			this.smsStrategy = new AliyunSmsStrategy(platform);
		} else if (platform.getSmsPlatformType() == SmsPlatformType.TENCENT) {
			this.smsStrategy = new TencentSmsStrategy(platform);
		} else if (platform.getSmsPlatformType() == SmsPlatformType.QINIU) {
			this.smsStrategy = new QiniuSmsStrategy(platform);
		} else if (platform.getSmsPlatformType() == SmsPlatformType.HUAWEI) {
			this.smsStrategy = new HuaweiSmsStrategy(platform);
		} else {
//			unknow sms strategy
			throw new SwdaException("未知的短信平台服务");
		}
	}

	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	public void send(String mobile, Map<String, String> params) {
		smsStrategy.send(mobile, params);
	}

}
