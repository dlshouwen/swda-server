package com.dlshouwen.swda.core.security.cache;

import cn.hutool.core.collection.ListUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.core.common.cache.RedisCache;
import com.dlshouwen.swda.core.common.constant.Constant;
import com.dlshouwen.swda.core.security.properties.TokenProperties;
import com.dlshouwen.swda.core.security.user.UserDetail;

import java.util.List;
import java.util.Set;

/**
 * token cache
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Component
@AllArgsConstructor
public class TokenCache {

	/** redis cache */
	private final RedisCache redisCache;
	
	/** token properties */
	private final TokenProperties tokenProperties;

	/**
	 * save user
	 * @param accessToken
	 * @param user
	 */
	public void saveUser(String accessToken, UserDetail user) {
		redisCache.set(Constant.TOKEN_PREFIX + accessToken, user, tokenProperties.getAccessTokenExpire());
	}

	/**
	 * save user
	 * @param accessToken
	 * @param user
	 * @param expire
	 */
	public void saveUser(String accessToken, UserDetail user, long expire) {
		redisCache.set(Constant.TOKEN_PREFIX + accessToken, user, expire);
	}

	/**
	 * get expire
	 * @param accessToken
	 * @return expire
	 */
	public Long getExpire(String accessToken) {
		return redisCache.getExpire(Constant.TOKEN_PREFIX + accessToken);
	}

	/**
	 * get user
	 * @param accessToken
	 * @return user
	 */
	public UserDetail getUser(String accessToken) {
		return (UserDetail) redisCache.get(Constant.TOKEN_PREFIX + accessToken);
	}

	/**
	 * delete user
	 * @param accessToken
	 */
	public void deleteUser(String accessToken) {
		redisCache.delete(Constant.TOKEN_PREFIX + accessToken);
	}

	/**
	 * get user token list
	 * @return user token list
	 */
	public List<String> getUserTokenList() {
		Set<String> sets = redisCache.keys(Constant.TOKEN_PREFIX + "*");
		return ListUtil.toList(sets);
	}

}
