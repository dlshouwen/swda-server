package com.dlshouwen.swda.core.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Component
public class RedisCache {
	
	/** redis template */
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/** default expire */
	public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
	
	/** expire: one hour */
	public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
	
	/** expire: six hour */
	public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;

	/** not expire */
	public final static long NOT_EXPIRE = -1L;

	/**
	 * set
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void set(String key, Object value, long expire) {
		redisTemplate.opsForValue().set(key, value);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
	}

	/**
	 * set
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * get
	 * @param key
	 * @param expire
	 * @return data
	 */
	public Object get(String key, long expire) {
		Object value = redisTemplate.opsForValue().get(key);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
		return value;
	}

	/**
	 * get
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return get(key, NOT_EXPIRE);
	}

	/**
	 * increment
	 * @param key
	 * @return
	 */
	public Long increment(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	/**
	 * has key
	 * @param key
	 * @return
	 */
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * keys
	 * @param pattern
	 * @return keys
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * delete
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * delete
	 * @param keys
	 */
	public void delete(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * hash get
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hGet(String key, String field) {
		return redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * hash get all
	 * @param key
	 * @return
	 */
	public Map<String, Object> hGetAll(String key) {
		HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
		return hashOperations.entries(key);
	}

	/**
	 * hash map set
	 * @param key
	 * @param map
	 */
	public void hMSet(String key, Map<String, Object> map) {
		hMSet(key, map, DEFAULT_EXPIRE);
	}

	/**
	 * hash map set
	 * @param key
	 * @param map
	 * @param expire
	 */
	public void hMSet(String key, Map<String, Object> map, long expire) {
		redisTemplate.opsForHash().putAll(key, map);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
	}

	/**
	 * hash set
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hSet(String key, String field, Object value) {
		hSet(key, field, value, DEFAULT_EXPIRE);
	}

	/**
	 * hash set
	 * @param key
	 * @param field
	 * @param value
	 * @param expire
	 */
	public void hSet(String key, String field, Object value, long expire) {
		redisTemplate.opsForHash().put(key, field, value);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
	}

	/**
	 * expire
	 * @param key
	 * @param expire
	 */
	public void expire(String key, long expire) {
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	/**
	 * expire at
	 * @param key
	 * @param expire
	 */
	public void expireAt(String key, Date expire) {
		redisTemplate.expireAt(key, expire);
	}

	/**
	 * get expire
	 * @param key
	 * @return
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * hash delete
	 * @param key
	 * @param fields
	 */
	public void hDel(String key, Object... fields) {
		redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * left push
	 * @param key
	 * @param value
	 */
	public void leftPush(String key, Object value) {
		leftPush(key, value, DEFAULT_EXPIRE);
	}

	/**
	 * left push
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void leftPush(String key, Object value, long expire) {
		redisTemplate.opsForList().leftPush(key, value);
		if (expire != NOT_EXPIRE) {
			expire(key, expire);
		}
	}

	/**
	 * right pop
	 * @param key
	 * @return
	 */
	public Object rightPop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}

}