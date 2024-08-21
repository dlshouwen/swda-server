package com.dlshouwen.swda.bms.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.bms.system.entity.RolePermission;
import com.dlshouwen.swda.bms.system.mapper.RolePermissionMapper;
import com.dlshouwen.swda.bms.system.service.IRolePermissionService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * role permission service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

	/**
	 * get permission id list
	 * @param roleId
	 * @return permission id list
	 */
	@Override
	public List<Long> getPermissionIdList(Long roleId) {
		return baseMapper.getPermissionIdList(roleId);
	}
	
	/**
	 * save or update
	 * @param roleId
	 * @param permissionIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> permissionIdList) {
//		get permission id list
		List<Long> dbPermissionIdList = this.getPermissionIdList(roleId);
//		get insert permission datas
		Collection<Long> insertPermissionIdList = CollUtil.subtract(permissionIdList, dbPermissionIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertPermissionIdList)) {
//			construct role permission list by permission id
			List<RolePermission> permissionList = insertPermissionIdList.stream().map(permissionId -> {
//				create role permission
				RolePermission entity = new RolePermission();
//				set permission id, role id
				entity.setPermissionId(permissionId);
				entity.setRoleId(roleId);
//				return role permission
				return entity;
			}).collect(Collectors.toList());
//			batch insert role permission list
			saveBatch(permissionList);
		}
//		get delete permission id list
		Collection<Long> deletePermissionIdList = CollUtil.subtract(dbPermissionIdList, permissionIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deletePermissionIdList)) {
//			get wrapper
			LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
//			delete role permission list
			remove(queryWrapper.eq(RolePermission::getRoleId, roleId).in(RolePermission::getPermissionId, deletePermissionIdList));
		}
	}

	/**
	 * delete role permission by role id list
	 * @param roleIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRolePermissionByRoleIdList(List<Long> roleIdList) {
//		delete role permission list
		remove(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIdList));
	}

	/**
	 * delete role permission by permission id
	 * @param permissionId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRolePermissionByPermissionId(Long permissionId) {
//		delete role permission list
		remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, permissionId));
	}

}