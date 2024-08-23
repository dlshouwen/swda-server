package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.grid.utils.GridUtils;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.auth.service.IUserTokenService;
import com.dlshouwen.swda.bms.system.convert.RoleConvert;
import com.dlshouwen.swda.bms.system.dict.DataScopeType;
import com.dlshouwen.swda.bms.system.entity.Role;
import com.dlshouwen.swda.bms.system.mapper.RoleMapper;
import com.dlshouwen.swda.bms.system.service.IRoleOrganService;
import com.dlshouwen.swda.bms.system.service.IRolePermissionService;
import com.dlshouwen.swda.bms.system.service.IRoleService;
import com.dlshouwen.swda.bms.system.service.IUserRoleService;
import com.dlshouwen.swda.bms.system.vo.RoleDataScopeVO;
import com.dlshouwen.swda.bms.system.vo.RoleVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * role service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {
	
	/** role permission service */
	private final IRolePermissionService rolePermissionService;
	
	/** role organ service */
	private final IRoleOrganService roleOrganService;
	
	/** user role service */
	private final IUserRoleService userRoleService;
	
	/** user token service */
	private final IUserTokenService userTokenService;

	/**
	 * get role list
	 * @param query
	 * @return role list
	 */
	@Override
	public PageResult<RoleVO> getRoleList(Query<Role> query) {
//		query page
		IPage<Role> page = GridUtils.query(baseMapper, query);
//		convert to vo for return
		return new PageResult<>(RoleConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * get role list
	 * @return role vo list
	 */
	@Override
	public List<RoleVO> getRoleList() {
//		get role list
		List<Role> roleList = this.list();
//		convert to role vo list for result
		return RoleConvert.INSTANCE.convert2VOList(roleList);
	}
	
	/**
	 * get role data
	 * @param roleId
	 * @return role data
	 */
	@Override
	public RoleVO getRoleData(Long roleId) {
//		get role data
		Role role = this.getById(roleId);
//		convert to role vo for return
		return RoleConvert.INSTANCE.convert2VO(role);
	}

	/**
	 * add role
	 * @param roleVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRole(RoleVO roleVO) {
//		convert to role
		Role role = RoleConvert.INSTANCE.convert(roleVO);
//		set data scope
		role.setDataScope(DataScopeType.SELF);
//		insert role
		this.save(role);
//		save role permission
		rolePermissionService.saveOrUpdate(roleVO.getRoleId(), roleVO.getPermissionIdList());
	}

	/**
	 * update role
	 * @param roleVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRole(RoleVO roleVO) {
//		convert to role
		Role role = RoleConvert.INSTANCE.convert(roleVO);
//		update role
		this.updateById(role);
//		save role permission
		rolePermissionService.saveOrUpdate(roleVO.getRoleId(), roleVO.getPermissionIdList());
//		update user cache
		userTokenService.updateUserCacheByRoleId(roleVO.getRoleId());
	}

	/**
	 * set role data scope
	 * @param roleDataScopeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setRoleDataScope(RoleDataScopeVO roleDataScopeVO) {
//		get role
		Role role = getById(roleDataScopeVO.getRoleId());
//		set data scope
		role.setDataScope(roleDataScopeVO.getDataScope());
//		update role
		updateById(role);
//		if custom data scope
		if (roleDataScopeVO.getDataScope().equals(DataScopeType.CUSTOM)) {
//			update role data scope
			roleOrganService.saveOrUpdate(role.getRoleId(), roleDataScopeVO.getOrganIdList());
		} else {
//			delete role organ
			roleOrganService.deleteRoleOrganByRoleIdList(Collections.singletonList(roleDataScopeVO.getRoleId()));
		}
//		update auth cache
		userTokenService.updateUserCacheByRoleId(roleDataScopeVO.getRoleId());
	}

	/**
	 * delete role
	 * @param roleIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(List<Long> roleIdList) {
//		dlete role list
		this.removeByIds(roleIdList);
//		delete user role list
		userRoleService.deleteUserRoleByRoleIdList(roleIdList);
//		delete role permission list
		rolePermissionService.deleteRolePermissionByRoleIdList(roleIdList);
//		delete role organ list
		roleOrganService.deleteRoleOrganByRoleIdList(roleIdList);
//		update auth cache
		roleIdList.forEach(userTokenService::updateUserCacheByRoleId);
	}

	/**
	 * get role name list
	 * @param roleIdList
	 * @return role name list
	 */
	@Override
	public List<String> getRoleNameList(List<Long> roleIdList) {
//		if id list is empty then return null
		if (roleIdList.isEmpty()) {
			return null;
		}
//		get role name list for return
		return this.listByIds(roleIdList).stream().map(Role::getRoleName).toList();
	}

}