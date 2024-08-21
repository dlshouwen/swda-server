package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.bms.system.entity.RoleOrgan;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

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
	 * delete role organ by role id list
	 * @param roleIdList
	 */
	void deleteRoleOrganByRoleIdList(List<Long> roleIdList);

}