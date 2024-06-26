package com.dlshouwen.swda.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.auth.dao.SysRoleMenuDao;
import com.dlshouwen.swda.auth.entity.SysRoleMenuEntity;
import com.dlshouwen.swda.auth.service.SysRoleMenuService;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;

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
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity>
		implements SysRoleMenuService {

	/**
	 * save or update
	 * @param roleId
	 * @param menuIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
//		get menu id list
		List<Long> dbMenuIdList = getMenuIdList(roleId);
//		get insert menu id list
		Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
//		if not empty
		if (CollUtil.isNotEmpty(insertMenuIdList)) {
//			get insert menu list
			List<SysRoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
//				create role menu
				SysRoleMenuEntity entity = new SysRoleMenuEntity();
//				set menu id, role id
				entity.setMenuId(menuId);
				entity.setRoleId(roleId);
//				return role menu
				return entity;
			}).collect(Collectors.toList());
//			batch insert
			saveBatch(menuList);
		}
//		get delete menu id list
		Collection<Long> deleteMenuIdList = CollUtil.subtract(dbMenuIdList, menuIdList);
//		if not empty
		if (CollUtil.isNotEmpty(deleteMenuIdList)) {
//			create query wrapper
			LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
//			remove menu role list
			remove(queryWrapper.eq(SysRoleMenuEntity::getRoleId, roleId).in(SysRoleMenuEntity::getMenuId, deleteMenuIdList));
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
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getRoleId, roleIdList));
	}

	/**
	 * delete by menu id
	 * @param menuId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getMenuId, menuId));
	}

}