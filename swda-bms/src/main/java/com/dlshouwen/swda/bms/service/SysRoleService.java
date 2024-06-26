package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.vo.SysRoleDataScopeVO;
import com.dlshouwen.swda.bms.vo.SysRoleVO;

import java.util.List;

/**
 * role service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<SysRoleVO> page(SysRoleQuery query);

	/**
	 * get role list
	 * @param query
	 * @return role vo list
	 */
	List<SysRoleVO> getList(SysRoleQuery query);

	/**
	 * save
	 * @param roleVO
	 */
	void save(SysRoleVO vo);

	/**
	 * update
	 * @param roleVO
	 */
	void update(SysRoleVO vo);

	/**
	 * data scope
	 * @param roleDataScopeVO
	 */
	void dataScope(SysRoleDataScopeVO vo);

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
