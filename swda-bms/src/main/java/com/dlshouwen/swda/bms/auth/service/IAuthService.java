package com.dlshouwen.swda.bms.auth.service;

import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.bms.auth.entity.Auth;
import com.dlshouwen.swda.bms.auth.vo.AuthVO;
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
	 * bind
	 * @param userId
	 * @param openType
	 * @param authUser
	 */
	void bind(Long userId, Integer openType, AuthUser authUser);

	/**
	 * unbind
	 * @param userId
	 * @param openType
	 */
	void unBind(Long userId, Integer openType);

	/**
	 * get user id by open type and open id
	 * @param openType
	 * @param openId
	 * @return user id
	 */
	Long getUserIdByOpenTypeAndOpenId(Integer openType, String openId);

}