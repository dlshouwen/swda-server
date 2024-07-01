package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.vo.RoleOrganVO;
import com.dlshouwen.swda.bms.vo.RoleVO;

import java.util.List;

/**
 * role service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysRoleService extends BaseService<Role> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<RoleVO> page(SysRoleQuery query);

	/**
	 * get role list
	 * @param query
	 * @return role vo list
	 */
	List<RoleVO> getList(SysRoleQuery query);

	/**
	 * save
	 * @param roleVO
	 */
	void save(RoleVO vo);

	/**
	 * update
	 * @param roleVO
	 */
	void update(RoleVO vo);

	/**
	 * data scope
	 * @param roleDataScopeVO
	 */
	void dataScope(RoleOrganVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

	/**
	 * get name list
	 * @param idList
	 * @return role name list
	 */
	List<String> getNameList(List<Long> idList);

}
