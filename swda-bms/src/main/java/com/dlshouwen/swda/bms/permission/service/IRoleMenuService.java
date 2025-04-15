package com.dlshouwen.swda.bms.permission.service;

import com.dlshouwen.swda.bms.permission.entity.RoleMenu;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * role permission service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IRoleMenuService extends BaseService<RoleMenu> {

	/**
	 * get permission id list
	 * @param roleId
	 * @return permission id list
	 */
	List<Long> getMenuIdList(Long roleId);

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
	void deleteRoleMenuByRoleIdList(List<Long> roleIdList);

	/**
	 * delete by menu id
	 * @param menuId
	 */
	void deleteRoleMenuByMenuId(Long menuId);

}