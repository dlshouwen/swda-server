package com.dlshouwen.swda.bms.service;

import me.zhyd.oauth.model.AuthUser;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Auth;
import com.dlshouwen.swda.bms.vo.AuthVO;

import java.util.List;

/**
 * third login service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysThirdLoginService extends BaseService<Auth> {

	/**
	 * list by user id
	 * @param userId
	 * @return third login vo list
	 */
	List<AuthVO> listByUserId(Long userId);

	/**
	 * unbind
	 * @param userId
	 * @param openType
	 */
	void unBind(Long userId, String openType);

	/**
	 * bind
	 * @param userId
	 * @param openType
	 * @param authUser
	 */
	void bind(Long userId, String openType, AuthUser authUser);

	/**
	 * get user id by open type and open id
	 * @param openType
	 * @param openId
	 * @return user id
	 */
	Long getUserIdByOpenTypeAndOpenId(String openType, String openId);

}