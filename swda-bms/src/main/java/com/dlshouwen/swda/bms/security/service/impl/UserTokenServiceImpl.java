package com.dlshouwen.swda.bms.security.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.enums.ResultCode;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.account.UserDetailsService;
import com.dlshouwen.swda.core.security.cache.TokenCache;
import com.dlshouwen.swda.core.security.properties.TokenProperties;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.core.security.utils.TokenUtils;
import com.dlshouwen.swda.bms.auth.vo.UserTokenVO;
import com.dlshouwen.swda.bms.security.convert.UserTokenConvert;
import com.dlshouwen.swda.bms.security.entity.UserToken;
import com.dlshouwen.swda.bms.security.mapper.UserTokenMapper;
import com.dlshouwen.swda.bms.security.service.IUserTokenService;
import com.dlshouwen.swda.bms.system.convert.UserConvert;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * user token service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class UserTokenServiceImpl extends BaseServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {
	
	/** token store cache */
	private final TokenCache tokenStoreCache;
	
	/** user details service */
	private final UserDetailsService userDetailsService;
	
	/** token properties */
	private final TokenProperties securityProperties;
	
	/** user mapper */
	private final UserMapper userMapper;

	/**
	 * create token
	 * @param userId
	 * @return user token vo
	 */
	@Override
	public UserTokenVO createToken(Long userId) {
//		generate access token
		String accessToken = TokenUtils.generator();
//		generate refresh token
		String refreshToken = TokenUtils.generator();
//		create user token
		UserToken userToken = new UserToken();
//		set user id, access token , refresh token
		userToken.setUserId(userId);
		userToken.setAccessToken(accessToken);
		userToken.setRefreshToken(refreshToken);
//		get now date
		Date now = new Date();
//		set access token expire
		userToken.setAccessTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getAccessTokenExpire())));
//		set refresh token expire
		userToken.setRefreshTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getRefreshTokenExpire())));
//		get user token
		UserToken dbUserToken = baseMapper.selectOne(Wrappers.<UserToken>lambdaQuery().eq(UserToken::getUserId, userId));
//		if token is null
		if (dbUserToken == null) {
//			insert user
			baseMapper.insert(userToken);
		} else {
//			set token
			userToken.setRelationId(dbUserToken.getRelationId());
//			update user
			baseMapper.updateById(userToken);
		}
//		return user token
		return UserTokenConvert.INSTANCE.convert2VO(userToken);
	}

	/**
	 * refresh token
	 * @param refresh token
	 * @return user token vo
	 */
	@Override
	public UserTokenVO refreshToken(String refreshToken) {
//		create wrapper
		LambdaQueryWrapper<UserToken> query = Wrappers.lambdaQuery();
//		set wrapper
		query.eq(UserToken::getRefreshToken, refreshToken);
		query.ge(UserToken::getRefreshTokenExpire, new Date());
//		get user token
		UserToken entity = baseMapper.selectOne(query);
//		if user token is null
		if (entity == null) {
//			throw exception
			throw new SwdaException(ResultCode.REFRESH_TOKEN_INVALID);
		}
//		delete user cache
		tokenStoreCache.deleteUser(entity.getAccessToken());
//		generate access token
		String accessToken = TokenUtils.generator();
//		set access token
		entity.setAccessToken(accessToken);
//		set access token expire
		entity.setAccessTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(new Date(), securityProperties.getAccessTokenExpire())));
//		update user token
		baseMapper.updateById(entity);
//		get user
		User user = userMapper.selectById(entity.getUserId());
//		convert to user detail
		UserDetail userDetail = UserConvert.INSTANCE.convert2Detail(user);
//		get user details
		userDetailsService.getUserDetails(userDetail);
//		save user cache
		tokenStoreCache.saveUser(accessToken, userDetail);
//		convert to user token for return
		return UserTokenConvert.INSTANCE.convert2VO(entity);
	}

	/**
	 * expire token
	 * @param userId
	 */
	@Override
	public void expireToken(Long userId) {
//		create user token
		UserToken entity = new UserToken();
//		get now date
		LocalDateTime now = LocalDateTime.now();
//		set access token expire, refresh token expire
		entity.setAccessTokenExpire(now);
		entity.setRefreshTokenExpire(now);
//		update user token
		baseMapper.update(entity, Wrappers.<UserToken>lambdaQuery().eq(UserToken::getUserId, userId));
	}

	/**
	 * update user cache by role id
	 * @param roleId
	 */
	@Async
	@Override
	public void updateUserCacheByRoleId(Long roleId) {
//		get online access token list by role id
		List<String> accessTokenList = baseMapper.getOnlineAccessTokenListByRoleId(roleId, LocalDateTime.now());
//		update cache auth
		accessTokenList.forEach(this::updateCacheAuth);
	}

	/**
	 * update user cache by user id
	 * @param userId
	 */
	@Async
	@Override
	public void updateUserCacheByUserId(Long userId) {
//		get online access token list by user id
		List<String> accessTokenList = baseMapper.getOnlineAccessTokenListByUserId(userId, LocalDateTime.now());
//		update cache auth
		accessTokenList.forEach(this::updateCacheAuth);
	}

	/**
	 * update cache auth
	 * @param accessToken
	 */
	private void updateCacheAuth(String accessToken) {
//		get user detail
		UserDetail user = tokenStoreCache.getUser(accessToken);
//		user is null then return
		if (user == null) {
			return;
		}
//		get expire
		Long expire = tokenStoreCache.getExpire(accessToken);
//		expire is null then return 
		if (expire == null) {
			return;
		}
//		get user details
		userDetailsService.getUserDetails(user);
//		save user
		tokenStoreCache.saveUser(accessToken, user, expire);
	}

}