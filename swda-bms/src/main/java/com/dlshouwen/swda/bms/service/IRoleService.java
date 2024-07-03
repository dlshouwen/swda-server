package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.vo.RoleDataScopeVO;
import com.dlshouwen.swda.bms.vo.RoleVO;

import java.util.List;

/**
 * role service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IRoleService extends BaseService<Role> {

	/**
	 * get role list
	 * @param query
	 * @return page result
	 */
	PageResult<RoleVO> getRoleList(Query<Role> query);

	/**
	 * get role list
	 * @param query
	 * @return role vo list
	 */
	List<RoleVO> getRoleList();
	
	/**
	 * get role data
	 * @param roleId
	 * @return role data
	 */
	RoleVO getRoleData(Long roleId);

	/**
	 * add role
	 * @param roleVO
	 */
	void addRole(RoleVO vo);

	/**
	 * update role
	 * @param roleVO
	 */
	void updateRole(RoleVO vo);

	/**
	 * set role data scope
	 * @param roleDataScopeVO
	 */
	void setRoleDataScope(RoleDataScopeVO roleDataScopeVO);

	/**
	 * delete role
	 * @param roleIdList
	 */
	void deleteRole(List<Long> roleIdList);

	/**
	 * get role name list
	 * @param roleIdList
	 * @return role name list
	 */
	List<String> getRoleNameList(List<Long> roleIdList);

}
