package com.dlshouwen.swda.auth.service;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysThirdLoginEntity;
import com.dlshouwen.swda.auth.vo.SysThirdLoginVO;
import com.dlshouwen.swda.core.service.BaseService;

import me.zhyd.oauth.model.AuthUser;

/**
 * third login service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysThirdLoginService extends BaseService<SysThirdLoginEntity> {

	/**
	 * list by user id
	 * @param userId
	 * @return third login vo list
	 */
	List<SysThirdLoginVO> listByUserId(Long userId);

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