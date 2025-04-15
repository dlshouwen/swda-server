package com.dlshouwen.swda.bms.permission.service.impl;

import cn.hutool.core.collection.CollUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.permission.entity.RoleOrgan;
import com.dlshouwen.swda.bms.permission.mapper.RoleOrganMapper;
import com.dlshouwen.swda.bms.permission.service.IRoleOrganService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * role data scope service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
public class RoleOrganServiceImpl extends BaseServiceImpl<RoleOrganMapper, RoleOrgan> implements IRoleOrganService {

	/**
	 * save or update
	 * @param roleId
	 * @param organIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> organIdList) {
//		get organ id list
		List<Long> dbOrganIdList = this.getOrganIdList(roleId);
//		get insert organ id list
		Collection<Long> insertOrganIdList = CollUtil.subtract(organIdList, dbOrganIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertOrganIdList)) {
//			construct role data scope list by organ id list
			List<RoleOrgan> orgList = insertOrganIdList.stream().map(organId -> {
//				create role data scope
				RoleOrgan entity = new RoleOrgan();
//				set organ id, role id
				entity.setOrganId(organId);
				entity.setRoleId(roleId);
//				return role data scope
				return entity;
			}).collect(Collectors.toList());
//			batch insert role data scope
			saveBatch(orgList);
		}
//		get delete organ id list
		Collection<Long> deleteOrganIdList = CollUtil.subtract(dbOrganIdList, organIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deleteOrganIdList)) {
//			get wrapper
			LambdaQueryWrapper<RoleOrgan> queryWrapper = Wrappers.<RoleOrgan>lambdaQuery();
//			set condition
			queryWrapper.eq(RoleOrgan::getRoleId, roleId);
			queryWrapper.in(RoleOrgan::getOrganId, deleteOrganIdList);
//			delete role data scope
			remove(queryWrapper);
		}
	}

	/**
	 * get organ id list
	 * @param roleId
	 * @return organ id list
	 */
	@Override
	public List<Long> getOrganIdList(Long roleId) {
//		get organ id list
		return baseMapper.getOrganIdList(roleId);
	}

	/**
	 * delete role organ by role id list
	 * @param roleIdList
	 */
	@Override
	public void deleteRoleOrganByRoleIdList(List<Long> roleIdList) {
//		delete role organ by role id list
		remove(Wrappers.<RoleOrgan>lambdaQuery().in(RoleOrgan::getRoleId, roleIdList));
	}

}