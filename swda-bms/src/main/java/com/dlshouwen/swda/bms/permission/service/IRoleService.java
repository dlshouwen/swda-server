package com.dlshouwen.swda.bms.permission.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.permission.entity.Role;
import com.dlshouwen.swda.bms.permission.vo.RoleDataScopeVO;
import com.dlshouwen.swda.bms.permission.vo.RoleVO;

import java.util.List;

/**
 * role service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IRoleService extends BaseService<Role> {

	/**
	 * get role page result
	 * @param query
	 * @return role page result
	 */
	PageResult<RoleVO> getRolePageResult(Query<Role> query);

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
