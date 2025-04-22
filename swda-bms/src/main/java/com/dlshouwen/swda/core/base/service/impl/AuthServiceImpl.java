package com.dlshouwen.swda.core.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.base.convert.AuthConvert;
import com.dlshouwen.swda.core.base.entity.Auth;
import com.dlshouwen.swda.core.base.mapper.AuthMapper;
import com.dlshouwen.swda.core.base.service.IAuthService;
import com.dlshouwen.swda.core.base.vo.AuthVO;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * auth service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
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
	 * bind auth
	 * @param userId
	 * @param openType
	 * @param authUser
	 */
	@Override
	public void bindAuth(Long userId, String openType, AuthUser authUser) {
//		create auth
		Auth auth = new Auth();
//		set open type, open id, username, user id, real name
		auth.setOpenType(openType);
		auth.setOpenId(authUser.getUuid());
		auth.setUsername(authUser.getUsername());
		auth.setUserId(userId);
//		insert third login
		baseMapper.insert(auth);
	}

	/**
	 * unbind auth
	 * @param userId
	 * @param openType
	 */
	@Override
	public void unbindAuth(Long userId, String openType) {
//		delete bind info
		baseMapper.delete(Wrappers.<Auth>lambdaQuery().eq(Auth::getUserId, userId).eq(Auth::getOpenType, openType));
	}

	/**
	 * get user id by open type and open id
	 * @param openType
	 * @param openId
	 * @return user id
	 */
	@Override
	public Long getUserIdByOpenTypeAndOpenId(String openType, String openId) {
//		get auth
		Auth auth = baseMapper.selectOne(Wrappers.<Auth>lambdaQuery().eq(Auth::getOpenType, openType).eq(Auth::getOpenId, openId));
//		if auth empty
		if (auth == null) {
//			throw exception
			throw new SwdaException("还未绑定用户，请先绑定用户");
		}
//		return user id
		return auth.getUserId();
	}

}