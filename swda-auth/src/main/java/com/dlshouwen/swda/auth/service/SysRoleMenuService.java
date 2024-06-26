package com.dlshouwen.swda.auth.service;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysRoleMenuEntity;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * role menu service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

	/**
	 * get menu id list
	 * @param roleId
	 * @return menu id list
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
	void deleteByRoleIdList(List<Long> roleIdList);

	/**
	 * delete by menu id
	 * @param menuId
	 */
	void deleteByMenuId(Long menuId);

}