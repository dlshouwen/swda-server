package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.UserToken;
import com.dlshouwen.swda.bms.vo.UserTokenVO;

/**
 * user token service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysUserTokenService extends BaseService<UserToken> {

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
	void updateCacheAuthByRoleId(Long roleId);

	/**
	 * update cache auth by user id
	 * @param userId
	 */
	void updateCacheAuthByUserId(Long userId);

}