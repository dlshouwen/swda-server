package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.mapper.SysRoleDataScopeDao;
import com.dlshouwen.swda.bms.entity.SysRoleDataScopeEntity;
import com.dlshouwen.swda.bms.service.SysRoleDataScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * role data scope service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
public class SysRoleDataScopeServiceImpl extends BaseServiceImpl<SysRoleDataScopeDao, SysRoleDataScopeEntity> implements SysRoleDataScopeService {

	/**
	 * save or update
	 * @param roleId
	 * @param organIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> orgIdList) {
//		get organ id list
		List<Long> dbOrgIdList = getOrgIdList(roleId);
//		get insert organ id list
		Collection<Long> insertOrgIdList = CollUtil.subtract(orgIdList, dbOrgIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertOrgIdList)) {
//			construct role data scope list by organ id list
			List<SysRoleDataScopeEntity> orgList = insertOrgIdList.stream().map(orgId -> {
//				create role data scope
				SysRoleDataScopeEntity entity = new SysRoleDataScopeEntity();
//				set organ id, role id
				entity.setOrgId(orgId);
				entity.setRoleId(roleId);
//				return role data scope
				return entity;
			}).collect(Collectors.toList());
//			batch insert role data scope
			saveBatch(orgList);
		}
//		get delete organ id list
		Collection<Long> deleteOrgIdList = CollUtil.subtract(dbOrgIdList, orgIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deleteOrgIdList)) {
//			get wrapper
			LambdaQueryWrapper<SysRoleDataScopeEntity> queryWrapper = new LambdaQueryWrapper<>();
//			set condition
			queryWrapper.eq(SysRoleDataScopeEntity::getRoleId, roleId);
			queryWrapper.in(SysRoleDataScopeEntity::getOrgId, deleteOrgIdList);
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
	public List<Long> getOrgIdList(Long roleId) {
//		get organ id list
		return baseMapper.getOrgIdList(roleId);
	}

	/**
	 * delete by role id list
	 * @param roleIdList
	 */
	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
//		delete role data scope
		remove(new LambdaQueryWrapper<SysRoleDataScopeEntity>().in(SysRoleDataScopeEntity::getRoleId, roleIdList));
	}

}