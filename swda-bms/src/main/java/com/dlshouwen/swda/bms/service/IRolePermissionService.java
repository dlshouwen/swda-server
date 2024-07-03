package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.RolePermission;

import java.util.List;

/**
 * role permission service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IRolePermissionService extends BaseService<RolePermission> {

	/**
	 * get permission id list
	 * @param roleId
	 * @return permission id list
	 */
	List<Long> getPermissionIdList(Long roleId);

	/**
	 * save or update
	 * @param roleId
	 * @param menuIdList
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	void deleteRolePermissionByRoleIdList(List<Long> roleIdList);

	/**
	 * delete by menu id
	 * @param menuId
	 */
	void deleteRolePermissionByPermissionId(Long menuId);

}