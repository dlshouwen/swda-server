package com.dlshouwen.swda.bms.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.auth.service.IUserTokenService;
import com.dlshouwen.swda.bms.system.entity.UserRole;
import com.dlshouwen.swda.bms.system.mapper.UserRoleMapper;
import com.dlshouwen.swda.bms.system.service.IUserRoleService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

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
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
	
	/** user token service */
	private final IUserTokenService userTokenService;

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
			List<UserRole> roleList = insertRoleIdList.stream().map(roleId -> {
//				create user role
				UserRole entity = new UserRole();
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
			LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
//			delete user role
			remove(queryWrapper.eq(UserRole::getUserId, userId).in(UserRole::getRoleId, deleteRoleIdList));
		}
	}

	/**
	 * add user role
	 * @param roleId
	 * @param userIdList
	 */
	@Override
	public void addUserRole(Long roleId, List<Long> userIdList) {
//		for each user id list to construct user role list
		List<UserRole> list = userIdList.stream().map(userId -> {
//			create user role
			UserRole entity = new UserRole();
//			set user id, role id
			entity.setUserId(userId);
			entity.setRoleId(roleId);
//			return user role
			return entity;
		}).collect(Collectors.toList());
//		batch insert user role
		saveBatch(list);
//		update cache auth by user id
		userIdList.forEach(userTokenService::updateUserCacheByUserId);
	}

	/**
	 * delete user role
	 * @param roleId
	 * @param userIdList
	 */
	@Override
	public void deleteUserRole(Long roleId, List<Long> userIdList) {
//		create wrapper
		LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
//		delete user role
		remove(queryWrapper.eq(UserRole::getRoleId, roleId).in(UserRole::getUserId, userIdList));
//		update cache auth
		userIdList.forEach(userTokenService::updateUserCacheByUserId);
	}

	/**
	 * delete user role by role id list
	 * @param roleIdList
	 */
	@Override
	public void deleteUserRoleByRoleIdList(List<Long> roleIdList) {
//		delete user role
		remove(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, roleIdList));
	}

	/**
	 * delete user role by user id list
	 * @param userIdList
	 */
	@Override
	public void deleteUserRoleByUserIdList(List<Long> userIdList) {
//		delete user role
		remove(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, userIdList));
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