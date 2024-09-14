package com.dlshouwen.swda.bms.core.email.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.core.common.cache.RedisCache;

import java.util.List;

/**
 * email cache
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class EmailCache {
	
	/** redis cache */
	private final RedisCache redisCache;

	/** mail round key */
	private final String MAIL_ROUND_KEY = "bms:mail:round";
	
	/** mail platform key */
	private final String MAIL_PLATFORM_KEY = "bms:mail:platform";

	/**
	 * get round value
	 * @return round value
	 */
	public Long getRoundValue() {
		return redisCache.increment(MAIL_ROUND_KEY);
	}

	/**
	 * get group round value
	 * @param groupName
	 * @return code value
	 */
	public Long getRoundValue(String groupName) {
		return redisCache.increment(MAIL_ROUND_KEY+":"+groupName);
	}

	/**
	 * get email platform list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmailPlatform> getEmailPlatformList() {
		return (List<EmailPlatform>) redisCache.get(MAIL_PLATFORM_KEY);
	}

	/**
	 * save email platform
	 * @param list
	 */
	public void saveEmailPlatform(List<EmailPlatform> list) {
		redisCache.set(MAIL_PLATFORM_KEY, list);
	}

	/**
	 * delete email platform
	 */
	public void deleteAllEmailPlatform() {
		redisCache.delete(MAIL_PLATFORM_KEY);
	}

}
