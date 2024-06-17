package com.dlshouwen.swda.sms.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.dict.CallResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.utils.ExceptionUtils;
import com.dlshouwen.swda.core.utils.JsonUtils;
import com.dlshouwen.swda.sms.cache.SmsCache;
import com.dlshouwen.swda.sms.context.SmsContext;
import com.dlshouwen.swda.sms.entity.SmsLog;
import com.dlshouwen.swda.sms.entity.SmsPlatform;

import java.util.List;
import java.util.Map;

/**
 * sms service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsService {

	/** sms platform service */
	private final SmsPlatformService smsPlatformService;

	/** sms log service */
	private final SmsLogService smsLogService;

	/** sms cache */
	private final SmsCache smsCache;

	/**
	 * send
	 * @param mobile
	 * @param params
	 * @return send result
	 */
	public boolean send(String mobile, Map<String, String> params) {
//		get enabled platform list
		List<SmsPlatform> platformList = smsPlatformService.getEnabledPlatformList();
//		if none then throw exception
		if (platformList == null || platformList.size() <= 0) {
			throw new SwdaException("无可用的短信服务平台");
		}
//		get round platform
		SmsPlatform platform = platformList.get((int) (smsCache.getRoundValue() % platformList.size()));
//		send message
		try {
//			new content & send message
			new SmsContext(platform).send(mobile, params);
//			save log
			saveLog(platform, mobile, params, null);
//			success
			return true;
		} catch (Exception e) {
//			log
			log.error("send message error, mobile: {}", mobile, e);
//			save log
			saveLog(platform, mobile, params, e);
//			failure
			return false;
		}
	}

	/**
	 * save log
	 * @param platform
	 * @param mobile
	 * @param params
	 * @param e
	 */
	public void saveLog(SmsPlatform platform, String mobile, Map<String, String> params, Exception e) {
//		set log
		SmsLog log = new SmsLog();
		log.setPlatformId(platform.getPlatformId());
		log.setPlatformType(platform.getPlatformType());
		log.setMobile(mobile);
		log.setParams(JsonUtils.toJsonString(params));
		log.setCallResult(CallResult.SUCCESS);
//		set error info
		if (e != null) {
//			set call result, error reason
			log.setCallResult(CallResult.FAILURE);
			log.setErrorReason(ExceptionUtils.toString(e));
		}
//		save log
		smsLogService.save(log);
	}

}
