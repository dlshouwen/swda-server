package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.mapper.SysRoleMenuDao;
import com.dlshouwen.swda.bms.entity.SysRoleMenuEntity;
import com.dlshouwen.swda.bms.service.SysRoleMenuService;
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
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

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
			List<SysRoleMenuEntity> menuList = insertMenuIdList.stream().map(menuId -> {
//				create role menu
				SysRoleMenuEntity entity = new SysRoleMenuEntity();
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
			LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
//			delete role menu list
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
//		delete role menu list
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getRoleId, roleIdList));
	}

	/**
	 * delete by menu id
	 * @param menuId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByMenuId(Long menuId) {
//		delete role menu list
		remove(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getMenuId, menuId));
	}

}