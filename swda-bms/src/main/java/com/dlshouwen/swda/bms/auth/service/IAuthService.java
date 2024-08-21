package com.dlshouwen.swda.bms.auth.service;

import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.bms.auth.entity.Auth;
import com.dlshouwen.swda.bms.auth.vo.AuthVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * auth service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IAuthService extends BaseService<Auth> {

	/**
	 * get auth list
	 * @param userId
	 * @return auth vo list
	 */
	List<AuthVO> getAuthList(Long userId);

	/**
	 * bind
	 * @param userId
	 * @param authPlatformType
	 * @param authUser
	 */
	void bind(Long userId, Integer authPlatformType, AuthUser authUser);

	/**
	 * unbind
	 * @param userId
	 * @param authPlatformType
	 */
	void unBind(Long userId, Integer authPlatformType);

	/**
	 * get user id by open type and open id
	 * @param authPlatformType
	 * @param openId
	 * @return user id
	 */
	Long getUserIdByOpenTypeAndOpenId(Integer authPlatformType, String openId);

}