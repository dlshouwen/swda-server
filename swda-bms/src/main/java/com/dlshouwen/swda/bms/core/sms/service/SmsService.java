package com.dlshouwen.swda.bms.core.sms.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.core.sms.cache.SmsCache;
import com.dlshouwen.swda.bms.core.sms.context.SmsContext;
import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.service.ISmsLogService;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.service.ISmsPlatformService;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.ExceptionUtils;
import com.dlshouwen.swda.core.common.utils.JsonUtils;
import com.dlshouwen.swda.core.log.dict.CallResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * sms service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsService {

	/** sms platform service */
	private final ISmsPlatformService smsPlatformService;

	/** sms log service */
	private final ISmsLogService smsLogService;

	/** sms cache */
	private final SmsCache smsCache;

	/**
	 * send
	 * @param mobile
	 * @param params
	 * @return send result
	 */
	public boolean send(String mobile, Map<String, String> params) {
//		get round sms platform
		SmsPlatform smsPlatform = this.roundSmsPlatform();
//		send
		return this.send(smsPlatform, mobile, params);
	}
	
	/**
	 * send
	 * @param smsPlatform
	 * @param mobile
	 * @param params
	 * @return send result
	 */
	public boolean send(SmsPlatform smsPlatform, String mobile, Map<String, String> params) {
//		send message
		try {
//			new content & send message
			new SmsContext(smsPlatform).send(mobile, params);
//			save log
			this.saveSmsLog(smsPlatform, mobile, params, null);
//			success
			return true;
		} catch (Exception e) {
//			log
			log.error("send message error, mobile: {}", mobile, e);
//			save log
			this.saveSmsLog(smsPlatform, mobile, params, e);
//			failure
			return false;
		}
	}

	/**
	 * round sms platform
	 * @return sms platform
	 */
	private SmsPlatform roundSmsPlatform() {
//		get enable sms platform list
		List<SmsPlatform> smsPlatformList = smsPlatformService.getEnableSmsPlatformList();
//		if sms platform list is empty
		if (smsPlatformList.isEmpty()) {
//			throw new exception
			throw new SwdaException("no avaliable sms platform");
		}
//		get round
		long round = smsCache.getRoundValue();
//		get round sms platform
		return smsPlatformList.get((int) round % smsPlatformList.size());
	}

	/**
	 * save log
	 * @param platform
	 * @param mobile
	 * @param params
	 * @param e
	 */
	public void saveSmsLog(SmsPlatform platform, String mobile, Map<String, String> params, Exception e) {
//		set log
		SmsLog log = new SmsLog();
		log.setSmsPlatformId(platform.getSmsPlatformId());
		log.setSmsPlatformName(platform.getSmsPlatformName());
		log.setSmsPlatformType(platform.getSmsPlatformType());
		log.setMobile(mobile);
		log.setParams(JsonUtils.toJsonString(params));
		log.setCallResult(CallResult.SUCCESS);
//		set error info
		if (e != null) {
//			set call result, error reason
			log.setCallResult(CallResult.FAILURE);
			log.setErrorReason(ExceptionUtils.toString(e));
		}
//		set send time
		log.setSendTime(LocalDateTime.now());
//		save log
		smsLogService.save(log);
	}

}
