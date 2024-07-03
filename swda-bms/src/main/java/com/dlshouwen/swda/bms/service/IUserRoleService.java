package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.UserRole;

import java.util.List;

/**
 * user role service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IUserRoleService extends BaseService<UserRole> {

	/**
	 * save or update
	 * @param userId
	 * @param roleIdList
	 */
	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * delete by user id list
	 * @param userIdList
	 */
	void deleteByUserIdList(List<Long> userIdList);

	/**
	 * delete role user
	 * @param roleId
	 * @param userIdList
	 */
	void deleteRoleUser(Long roleId, List<Long> userIdList);

	/**
	 * add role user
	 * @param roleId
	 * @param userIdList
	 */
	void addRoleUser(Long roleId, List<Long> userIdList);

	/**
	 * get role id list
	 * @param userId
	 * @return role id list
	 */
	List<Long> getRoleIdList(Long userId);

}