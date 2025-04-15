package com.dlshouwen.swda.bms.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.permission.entity.RoleMenu;
import com.dlshouwen.swda.bms.permission.mapper.RoleMenuMapper;
import com.dlshouwen.swda.bms.permission.service.IRoleMenuService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * role menu service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

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
	 * save or update
	 * @param roleId
	 * @param menuIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
//		get menu id list
		List<Long> dbMenuIdList = this.getMenuIdList(roleId);
//		get insert menu datas
		Collection<Long> insertMenuIdList = CollUtil.subtract(menuIdList, dbMenuIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertMenuIdList)) {
//			construct role menu list by menu id
			List<RoleMenu> menuList = insertMenuIdList.stream().map(menuId -> {
//				create role menu
				RoleMenu entity = new RoleMenu();
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
			LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.lambdaQuery();
//			delete role menu list
			remove(queryWrapper.eq(RoleMenu::getRoleId, roleId).in(RoleMenu::getMenuId, deleteMenuIdList));
		}
	}

	/**
	 * delete role menu by role id list
	 * @param roleIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleMenuByRoleIdList(List<Long> roleIdList) {
//		delete role menu list
		remove(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIdList));
	}

	/**
	 * delete role menu by menu id
	 * @param menuId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleMenuByMenuId(Long menuId) {
//		delete role menu list
		remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, menuId));
	}

}