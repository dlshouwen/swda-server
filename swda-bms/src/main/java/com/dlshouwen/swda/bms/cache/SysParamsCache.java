package com.dlshouwen.swda.bms.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.cache.RedisCache;

/**
 * params cache
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysParamsCache {
	
	/** redis cache */
	private final RedisCache redisCache;

	/** system params key */
	private final String SYSTEM_PARAMS_KEY = "system:params";

	/**
	 * get
	 * @param key
	 * @return value
	 */
	public String get(String key) {
		return (String) redisCache.hGet(SYSTEM_PARAMS_KEY, key);
	}

	/**
	 * save
	 * @param key
	 * @param value
	 */
	public void save(String key, String value) {
		redisCache.hSet(SYSTEM_PARAMS_KEY, key, value, RedisCache.NOT_EXPIRE);
	}

	/**
	 * delete
	 * @param keys
	 */
	public void delete(Object... keys) {
		redisCache.hDel(SYSTEM_PARAMS_KEY, keys);
	}

}
