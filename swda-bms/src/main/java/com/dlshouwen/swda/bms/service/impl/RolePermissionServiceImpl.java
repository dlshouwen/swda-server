package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.mapper.RolePermissionMapper;
import com.dlshouwen.swda.bms.entity.RolePermission;
import com.dlshouwen.swda.bms.service.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * role menu service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

	/**
	 * save or update
	 * @param roleId
	 * @parammenuIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
//		get menu id list
		List<Long> dbMenuIdList = getMenuIdList(roleId);
//		get insert menu datas
		Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertMenuIdList)) {
//			construct role menu list by menu id
			List<RolePermission> menuList = insertMenuIdList.stream().map(menuId -> {
//				create role menu
				RolePermission entity = new RolePermission();
//				set menu id, role id
				entity.setMenuId(menuId);
				entity.setRoleId(roleId);
//				return role menu
				return entity;
			}).collect(Collectors.toList());
//			batch insert role menu list
			saveBatch(menuList);
		}
//		get delete menu id list
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deleteMenuIdList)) {
//			get wrapper
			LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
//			delete role menu list
			remove(queryWrapper.eq(RolePermission::getRoleId, roleId).in(RolePermission::getMenuId, deleteMenuIdList));
		}
	}

	/**
	 * get menu id list
	 * @param roleId
	 * @return menu id list
	 */
	@Override
	public List<Long> getMenuIdList(Long roleId) {
		return baseMapper.getMenuIdList(roleId);
	}

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByRoleIdList(List<Long> roleIdList) {
//		delete role menu list
		remove(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIdList));
	}

	/**
	 * delete by menu id
	 * @param menuId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
//		delete role menu list
		remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getMenuId, menuId));
	}

}