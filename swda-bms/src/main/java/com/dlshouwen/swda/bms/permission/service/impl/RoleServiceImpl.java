package com.dlshouwen.swda.bms.permission.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.base.service.IUserTokenService;
import com.dlshouwen.swda.bms.permission.convert.RoleConvert;
import com.dlshouwen.swda.bms.permission.dict.DataScopeType;
import com.dlshouwen.swda.bms.permission.entity.Role;
import com.dlshouwen.swda.bms.permission.mapper.RoleMapper;
import com.dlshouwen.swda.bms.permission.service.IRoleOrganService;
import com.dlshouwen.swda.bms.permission.service.IRoleMenuService;
import com.dlshouwen.swda.bms.permission.service.IRoleService;
import com.dlshouwen.swda.bms.permission.service.IUserRoleService;
import com.dlshouwen.swda.bms.permission.vo.RoleVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * role service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {
	
	/** role menu service */
	private final IRoleMenuService roleMenuService;
	
	/** role organ service */
	private final IRoleOrganService roleOrganService;
	
	/** user role service */
	private final IUserRoleService userRoleService;
	
	/** user token service */
	private final IUserTokenService userTokenService;

	/**
	 * get role page result
	 * @param query
	 * @return role page result
	 */
	@Override
	public PageResult<RoleVO> getRolePageResult(Query<Role> query) {
//		query page
		IPage<Role> page = this.page(query);
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
//		insert role
		baseMapper.insert(role);
//		save role menu
		roleMenuService.saveOrUpdate(role.getSystemId(), role.getRoleId(), roleVO.getMenuIdList());
//		if custom data scope
		if (roleVO.getDataScope().equals(DataScopeType.CUSTOM)) {
//			update role data scope
			roleOrganService.saveOrUpdate(role.getSystemId(), role.getRoleId(), roleVO.getOrganIdList());
		} else {
//			delete role organ
			roleOrganService.deleteRoleOrganByRoleIdList(Collections.singletonList(roleVO.getRoleId()));
		}
//		update auth cache
		userTokenService.updateUserCacheByRoleId(roleVO.getRoleId());
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
//		save role menu
		roleMenuService.saveOrUpdate(role.getSystemId(), role.getRoleId(), roleVO.getMenuIdList());
//		if custom data scope
		if (roleVO.getDataScope().equals(DataScopeType.CUSTOM)) {
//			update role data scope
			roleOrganService.saveOrUpdate(role.getSystemId(), role.getRoleId(), roleVO.getOrganIdList());
		} else {
//			delete role organ
			roleOrganService.deleteRoleOrganByRoleIdList(Collections.singletonList(roleVO.getRoleId()));
		}
//		update user cache
		userTokenService.updateUserCacheByRoleId(roleVO.getRoleId());
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
//		delete role menu list
		roleMenuService.deleteRoleMenuByRoleIdList(roleIdList);
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