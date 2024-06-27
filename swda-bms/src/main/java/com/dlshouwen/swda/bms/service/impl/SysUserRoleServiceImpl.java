package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.mapper.SysUserRoleDao;
import com.dlshouwen.swda.bms.entity.SysUserRoleEntity;
import com.dlshouwen.swda.bms.service.SysUserRoleService;
import com.dlshouwen.swda.bms.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * user role service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
	
	/** user token service */
	private final SysUserTokenService sysUserTokenService;

	/**
	 * save or update
	 * @param userId
	 * @param roleIdList
	 */
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
//		get role id list
		List<Long> dbRoleIdList = getRoleIdList(userId);
//		get insert role id list
		Collection<Long> insertRoleIdList = CollUtil.subtract(roleIdList, dbRoleIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertRoleIdList)) {
//			for each id list to construct role list
			List<SysUserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
//				create user role
				SysUserRoleEntity entity = new SysUserRoleEntity();
//				set user id, role id
				entity.setUserId(userId);
				entity.setRoleId(roleId);
//				return user role
				return entity;
			}).collect(Collectors.toList());
//			batch insert role list
			saveBatch(roleList);
		}
//		get delete role id list
		Collection<Long> deleteRoleIdList = CollUtil.subtract(dbRoleIdList, roleIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deleteRoleIdList)) {
//			create query wrapper
			LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
//			delete user role
			remove(queryWrapper.eq(SysUserRoleEntity::getUserId, userId).in(SysUserRoleEntity::getRoleId, deleteRoleIdList));
		}
	}

	/**
	 * save user list
	 * @param role
	 * @param userIdList
	 */
	@Override
	public void saveUserList(Long roleId, List<Long> userIdList) {
//		for each user id list to construct user role list
		List<SysUserRoleEntity> list = userIdList.stream().map(userId -> {
//			create user role
			SysUserRoleEntity entity = new SysUserRoleEntity();
//			set user id, role id
			entity.setUserId(userId);
			entity.setRoleId(roleId);
//			return user role
			return entity;
		}).collect(Collectors.toList());
//		batch insert user role
		saveBatch(list);
//		update cache auth by user id
		userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
	}

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
//		delete user role
		remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getRoleId, roleIdList));
	}

	/**
	 * delete by user id list
	 * @param userIdList
	 */
	@Override
	public void deleteByUserIdList(List<Long> userIdList) {
//		delete user role
		remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId, userIdList));
	}

	/**
	 * delete by user id list
	 * @param roleId
	 * @param userIdList
	 */
	@Override
	public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
//		create wrapper
		LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
//		delete user role
		remove(queryWrapper.eq(SysUserRoleEntity::getRoleId, roleId).in(SysUserRoleEntity::getUserId, userIdList));
//		update cache auth
		userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
	}

	/**
	 * get role id list
	 * @param userId
	 * @return role id list
	 */
	@Override
	public List<Long> getRoleIdList(Long userId) {
//		get role id list
		return baseMapper.getRoleIdList(userId);
	}

}