package com.dlshouwen.swda.bms.system.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.base.cache.RedisCache;
import com.dlshouwen.swda.core.base.constant.Constant;

/**
 * attr cache
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class AttrCache {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * get
	 * @param key
	 * @return value
	 */
	public String get(String key) {
		return (String) redisCache.hGet(Constant.ATTR_KEY, key);
	}

	/**
	 * set
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		redisCache.hSet(Constant.ATTR_KEY, key, value, RedisCache.NOT_EXPIRE);
	}

	/**
	 * delete
	 * @param keys
	 */
	public void delete(Object... keys) {
		redisCache.hDel(Constant.ATTR_KEY, keys);
	}

}
