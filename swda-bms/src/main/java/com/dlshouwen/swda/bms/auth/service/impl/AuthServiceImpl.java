package com.dlshouwen.swda.bms.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.auth.convert.AuthConvert;
import com.dlshouwen.swda.bms.auth.entity.Auth;
import com.dlshouwen.swda.bms.auth.mapper.AuthMapper;
import com.dlshouwen.swda.bms.auth.service.IAuthService;
import com.dlshouwen.swda.bms.auth.vo.AuthVO;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * auth service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl extends BaseServiceImpl<AuthMapper, Auth> implements IAuthService {

	/**
	 * get auth list
	 * @param userId
	 * @return auth list
	 */
	@Override
	public List<AuthVO> getAuthList(Long userId) {
//		get auth list
		List<Auth> authList = this.list(Wrappers.<Auth>lambdaQuery().eq(Auth::getUserId, userId));
//		convert to auth vo list for return
		return AuthConvert.INSTANCE.convert2VOList(authList);
	}

	/**
	 * bind
	 * @param userId
	 * @param authPlatformType
	 * @param authUser
	 */
	@Override
	public void bind(Long userId, Integer authPlatformType, AuthUser authUser) {
//		create auth
		Auth auth = new Auth();
//		set user id, opentype, open id, username
		auth.setUserId(userId);
		auth.setUserName(authUser.getUsername());
		auth.setAuthPlatformType(authPlatformType);
		auth.setAuthPlatformUuid(authUser.getUuid());
//		insert third login
		baseMapper.insert(auth);
	}

	/**
	 * unbind
	 * @param userId
	 * @param authPlatformType
	 */
	@Override
	public void unBind(Long userId, Integer authPlatformType) {
//		delete bind info
		baseMapper.delete(Wrappers.<Auth>lambdaQuery().eq(Auth::getUserId, userId).eq(Auth::getAuthPlatformType, authPlatformType));
	}

	/**
	 * get user id by open type and open id
	 * @param authPlatformType
	 * @param openId
	 * @return user id
	 */
	@Override
	public Long getUserIdByOpenTypeAndOpenId(Integer authPlatformType, String openId) {
//		get third login
		Auth entity = baseMapper.selectOne(Wrappers.<Auth>lambdaQuery().eq(Auth::getAuthPlatformType, authPlatformType).eq(Auth::getAuthPlatformUuid, openId));
//		if third login empty
		if (entity == null) {
//			throw exception
			throw new SwdaException("还未绑定用户，请先绑定用户");
		}
//		return user id
		return entity.getUserId();
	}

}