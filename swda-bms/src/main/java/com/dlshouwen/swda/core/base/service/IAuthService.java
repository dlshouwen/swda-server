package com.dlshouwen.swda.core.base.service;

import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.core.base.entity.Auth;
import com.dlshouwen.swda.core.base.vo.AuthVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * auth service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IAuthService extends BaseService<Auth> {

	/**
	 * get auth list
	 * @param userId
	 * @return auth vo list
	 */
	List<AuthVO> getAuthList(Long userId);

	/**
	 * bind auth
	 * @param userId
	 * @param openType
	 * @param authUser
	 */
	void bindAuth(Long userId, Integer openType, AuthUser authUser);

	/**
	 * unbind auth
	 * @param userId
	 * @param openType
	 */
	void unbindAuth(Long userId, Integer openType);

	/**
	 * get user id by open type and open id
	 * @param openType
	 * @param openId
	 * @return user id
	 */
	Long getUserIdByOpenTypeAndOpenId(Integer openType, String openId);

}