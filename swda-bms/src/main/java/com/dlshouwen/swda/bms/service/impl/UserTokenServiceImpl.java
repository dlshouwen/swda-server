package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.cache.TokenCache;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.enums.ResultCode;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.property.TokenProperties;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.TokenUtils;
import com.dlshouwen.swda.bms.convert.UserConvert;
import com.dlshouwen.swda.bms.convert.UserTokenConvert;
import com.dlshouwen.swda.bms.mapper.UserMapper;
import com.dlshouwen.swda.bms.mapper.UserTokenMapper;
import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.entity.UserToken;
import com.dlshouwen.swda.bms.service.IUserDetailsService;
import com.dlshouwen.swda.bms.service.IUserTokenService;
import com.dlshouwen.swda.bms.vo.UserTokenVO;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * user token service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class UserTokenServiceImpl extends BaseServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {
	
	/** token store cache */
	private final TokenCache tokenStoreCache;
	
	/** user details service */
	private final IUserDetailsService sysUserDetailsService;
	
	/** token properties */
	private final TokenProperties securityProperties;
	
	/** user mapper */
	private final UserMapper sysUserDao;

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
		UserToken entity = new UserToken();
//		set user id, access token , refresh token
		entity.setUserId(userId);
		entity.setAccessToken(accessToken);
		entity.setRefreshToken(refreshToken);
//		get now date
		Date now = new Date();
//		set access token expire
		entity.setAccessTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getAccessTokenExpire())));
//		set refresh token expire
		entity.setRefreshTokenExpire(DateUtil.toLocalDateTime(DateUtil.offsetSecond(now, securityProperties.getRefreshTokenExpire())));
//		get user token
		UserToken tokenEntity = baseMapper.selectOne(new LambdaQueryWrapper<UserToken>().eq(UserToken::getUserId, userId));
//		if token is null
		if (tokenEntity == null) {
//			insert user
			baseMapper.insert(entity);
		} else {
//			set token
			entity.setId(tokenEntity.getId());
//			update user
			baseMapper.updateById(entity);
		}
//		return user token
		return UserTokenConvert.INSTANCE.convert(entity);
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
		User user = sysUserDao.selectById(entity.getUserId());
//		convert to user detail
		UserDetail userDetail = UserConvert.INSTANCE.convertDetail(user);
//		get user details
		sysUserDetailsService.getUserDetails(userDetail);
//		save user cache
		tokenStoreCache.saveUser(accessToken, userDetail);
//		convert to user token for return
		return UserTokenConvert.INSTANCE.convert(entity);
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
		baseMapper.update(entity, new LambdaQueryWrapper<UserToken>().eq(UserToken::getUserId, userId));
	}

	/**
	 * update cache auth by role id
	 * @param roleId
	 */
	@Async
	@Override
	public void updateCacheAuthByRoleId(Long roleId) {
//		get online access token list by role id
		List<String> accessTokenList = baseMapper.getOnlineAccessTokenListByRoleId(roleId, LocalDateTime.now());
//		update cache auth
		accessTokenList.forEach(this::updateCacheAuth);
	}

	/**
	 * update cache auth by user id
	 * @param userId
	 */
	@Async
	@Override
	public void updateCacheAuthByUserId(Long userId) {
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
		sysUserDetailsService.getUserDetails(user);
//		save user
		tokenStoreCache.saveUser(accessToken, user, expire);
	}

}