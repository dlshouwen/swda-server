package com.dlshouwen.swda.bms.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.TreeUtils;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.convert.PermissionConvert;
import com.dlshouwen.swda.bms.system.entity.Permission;
import com.dlshouwen.swda.bms.system.mapper.PermissionMapper;
import com.dlshouwen.swda.bms.system.service.IPermissionService;
import com.dlshouwen.swda.bms.system.service.IRolePermissionService;
import com.dlshouwen.swda.bms.system.vo.PermissionVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * menu service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {
	
	/** role menu service */
	private final IRolePermissionService rolePermissionService;

	/**
	 * get user menu list
	 * @param user
	 * @param permissionType
	 * @return permission vo list
	 */
	@Override
	public List<PermissionVO> getUserMenuList(UserDetail user, Integer permissionType) {
//		defined permission list
		List<Permission> permissionList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get permission list
			permissionList = baseMapper.getPermissionList(permissionType);
		} else {
//			get user permission list
			permissionList = baseMapper.getUserPermissionList(user.getUserId(), permissionType);
		}
//		build permission tree for return
		return TreeUtils.build(PermissionConvert.INSTANCE.convert2VOList(permissionList));
	}

	/**
	 * get user authority list
	 * @param user
	 * @return user authority list
	 */
	@Override
	public Set<String> getUserAuthorityList(UserDetail user) {
//		defined authority list
		List<String> authorityList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get authority list
			authorityList = baseMapper.getAuthorityList();
		} else {
//			get user authority list
			authorityList = baseMapper.getUserAuthorityList(user.getUserId());
		}
//		defined perms set
		Set<String> permsSet = new HashSet<>();
//		for each authority
		for (String authority : authorityList) {
//			blank then continue
			if (StrUtil.isBlank(authority)) {
				continue;
			}
//			add to perms
			permsSet.addAll(Arrays.asList(authority.trim().split(",")));
		}
//		return permission set
		return permsSet;
	}

	/**
	 * get permission list
	 * @param permissionType
	 * @return permission list
	 */
	@Override
	public List<PermissionVO> getPermissionList(Integer permissionType) {
//		get permission list
		List<Permission> permissionList = baseMapper.getPermissionList(permissionType);
//		build permission tree for return
		return TreeUtils.build(PermissionConvert.INSTANCE.convert2VOList(permissionList));
	}
	
	/**
	 * get permission data
	 * @param permissionId
	 * @return permission data
	 */
	@Override
	public PermissionVO getPermissionData(Long permissionId) {
//		get permission data
		Permission permission = this.getById(permissionId);
//		convert to vo for return
		return PermissionConvert.INSTANCE.convert2VO(permission);
	}

	/**
	 * add permission
	 * @param permissionVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addPermission(PermissionVO permissionVO) {
//		convert to permission
		Permission permission = PermissionConvert.INSTANCE.convert(permissionVO);
//		insert permission
		this.save(permission);
	}

	/**
	 * update permission
	 * @param permissionVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePermission(PermissionVO permissionVO) {
//		convert to permission
		Permission permission = PermissionConvert.INSTANCE.convert(permissionVO);
//		if permission id equals pre permission id
		if (permission.getPermissionId().equals(permission.getPrePermissionId())) {
//			throw exception
			throw new SwdaException("上级菜单不能为自己");
		}
//		update permission
		this.updateById(permission);
	}

	/**
	 * delete permission
	 * @param permissionId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deletePermission(Long permissionId) {
//		delete permission
		this.removeById(permissionId);
//		delete role permission
		rolePermissionService.deleteRolePermissionByPermissionId(permissionId);
	}

	/**
	 * get sub permission count
	 * @param prePermissionId
	 * @return sub permission count
	 */
	@Override
	public Long getSubPermissionCount(Long prePermissionId) {
		return this.count(new LambdaQueryWrapper<Permission>().eq(Permission::getPrePermissionId, prePermissionId));
	}

}