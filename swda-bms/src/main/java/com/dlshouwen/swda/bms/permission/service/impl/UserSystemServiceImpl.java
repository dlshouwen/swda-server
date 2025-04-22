package com.dlshouwen.swda.bms.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.permission.entity.UserSystem;
import com.dlshouwen.swda.bms.permission.mapper.UserSystemMapper;
import com.dlshouwen.swda.bms.permission.service.IUserSystemService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * user system service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
public class UserSystemServiceImpl extends BaseServiceImpl<UserSystemMapper, UserSystem> implements IUserSystemService {

	/**
	 * save or update
	 * @param userId
	 * @param systemIdList
	 */
	@Override
	public void saveOrUpdate(Long userId, List<Long> systemIdList) {
//		get system id list
		List<Long> dbSystemIdList = getSystemIdList(userId);
//		get insert system id list
		Collection<Long> insertSystemIdList = CollUtil.subtract(systemIdList, dbSystemIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertSystemIdList)) {
//			for each system id to construct user system list
			List<UserSystem> systemList = insertSystemIdList.stream().map(systemId -> {
//				create user system
				UserSystem entity = new UserSystem();
//				set user id, system id
				entity.setUserId(userId);
				entity.setSystemId(systemId);
//				return user system
				return entity;
			}).collect(Collectors.toList());
//			batch insert user system
			saveBatch(systemList);
		}
//		get delete system id list
		Collection<Long> deleteSystemIdList = CollUtil.subtract(dbSystemIdList, systemIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deleteSystemIdList)) {
//			get wrapper
			LambdaQueryWrapper<UserSystem> queryWrapper = Wrappers.lambdaQuery();
//			delete user system
			remove(queryWrapper.eq(UserSystem::getUserId, userId).in(UserSystem::getSystemId, deleteSystemIdList));
		}
	}

	/**
	 * delete user system by system id list
	 * @param systemIdList
	 */
	@Override
	public void deleteUserSystemBySystemIdList(List<Long> systemIdList) {
//		delete user system
		remove(Wrappers.<UserSystem>lambdaQuery().in(UserSystem::getSystemId, systemIdList));
	}

	/**
	 * delete user system by user id list
	 * @param userIdList
	 */
	@Override
	public void deleteUserSystemByUserIdList(List<Long> userIdList) {
//		delete user system
		remove(Wrappers.<UserSystem>lambdaQuery().in(UserSystem::getUserId, userIdList));
	}

	/**
	 * get system id list
	 * @param userId
	 * @return system id list
	 */
	@Override
	public List<Long> getSystemIdList(Long userId) {
//		get system id list for return
		return baseMapper.getSystemIdList(userId);
	}

}