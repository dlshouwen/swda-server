package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.RoleOrgan;

import java.util.List;

/**
 * role data scope service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IRoleOrganService extends BaseService<RoleOrgan> {

	/**
	 * save or update
	 * @param roleId
	 * @param orgIdList
	 */
	void saveOrUpdate(Long roleId, List<Long> orgIdList);

	/**
	 * get organ id list
	 * @param roleId
	 * @return organ id list
	 */
	List<Long> getOrganIdList(Long roleId);

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	void deleteByRoleIdList(List<Long> roleIdList);

}