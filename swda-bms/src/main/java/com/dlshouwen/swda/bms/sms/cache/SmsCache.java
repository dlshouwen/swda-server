package com.dlshouwen.swda.bms.sms.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.sms.entity.SmsPlatform;
import com.dlshouwen.swda.core.common.cache.RedisCache;

import java.util.List;

/**
 * sms cache
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsCache {

	/** redis cache */
	private final RedisCache redisCache;

	/** sms round key */
	private final String SMS_ROUND_KEY = "sms:round";

	/** sms platform key */
	private final String SMS_PLATFORM_KEY = "sms:platform";
	
	/** sms code key */
	private final String SMS_CODE_KEY = "sms:code:";

	/**
	 * get round value
	 * @return
	 */
	public Long getRoundValue() {
		return redisCache.increment(SMS_ROUND_KEY);
	}

	/**
	 * get platform list
	 * @return platform list
	 */
	@SuppressWarnings("unchecked")
	public List<SmsPlatform> getPlatformlist() {
		return (List<SmsPlatform>) redisCache.get(SMS_PLATFORM_KEY);
	}

	/**
	 * save platform list
	 * @param platformList
	 */
	public void savePlatformList(List<SmsPlatform> platformList) {
		redisCache.set(SMS_PLATFORM_KEY, platformList);
	}

	/**
	 * delete all plat form
	 */
	public void deleteAllPlatform() {
		redisCache.delete(SMS_PLATFORM_KEY);
	}

	/**
	 * save code
	 * @param mobile
	 * @param code
	 */
	public void saveCode(String mobile, String code) {
		redisCache.set(SMS_CODE_KEY+mobile, code, 10 * 60);
	}

	/**
	 * get code
	 * @param mobile
	 * @return
	 */
	public String getCode(String mobile) {
		return (String) redisCache.get(SMS_CODE_KEY+mobile);
	}

	/**
	 * delete code
	 * @param mobile
	 */
	public void deleteCode(String mobile) {
		redisCache.delete(SMS_CODE_KEY+mobile);
	}

}
