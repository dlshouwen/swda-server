package com.dlshouwen.swda.bms.app.service;

import com.dlshouwen.swda.bms.app.entity.UserToken;
import com.dlshouwen.swda.bms.app.vo.UserTokenVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * user token service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IUserTokenService extends BaseService<UserToken> {

	/**
	 * create token
	 * @param userId
	 * @return user token vo
	 */
	UserTokenVO createToken(Long userId);

	/**
	 * refresh token
	 * @param refreshToken
	 * @return user token vo
	 */
	UserTokenVO refreshToken(String refreshToken);

	/**
	 * expire token
	 * @param userId
	 */
	void expireToken(Long userId);

	/**
	 * update cache auth by role id
	 * @param roleId
	 */
	void updateUserCacheByRoleId(Long roleId);

	/**
	 * update cache auth by user id
	 * @param userId
	 */
	void updateUserCacheByUserId(Long userId);

}