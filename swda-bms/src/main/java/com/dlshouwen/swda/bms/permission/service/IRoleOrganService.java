package com.dlshouwen.swda.bms.permission.service;

import com.dlshouwen.swda.bms.permission.entity.RoleOrgan;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * role data scope service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IRoleOrganService extends BaseService<RoleOrgan> {

	/**
	 * save or update
	 * @param systemId
	 * @param roleId
	 * @param orgIdList
	 */
	void saveOrUpdate(Long systemId, Long roleId, List<Long> orgIdList);

	/**
	 * get organ id list
	 * @param roleId
	 * @return organ id list
	 */
	List<Long> getOrganIdList(Long roleId);

	/**
	 * delete role organ by role id list
	 * @param roleIdList
	 */
	void deleteRoleOrganByRoleIdList(List<Long> roleIdList);

}