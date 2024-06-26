package com.dlshouwen.swda.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dlshouwen.swda.auth.convert.SysMenuConvert;
import com.dlshouwen.swda.auth.dao.SysMenuDao;
import com.dlshouwen.swda.auth.entity.SysMenuEntity;
import com.dlshouwen.swda.auth.enums.SuperAdminEnum;
import com.dlshouwen.swda.auth.service.SysMenuService;
import com.dlshouwen.swda.auth.vo.SysMenuVO;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.TreeUtils;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

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
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

	/**
	 * get user menu list
	 * @param userDetail
	 * @param type
	 * @return menu vo list
	 */
	@Override
	public List<SysMenuVO> getUserMenuList(UserDetail user, Integer type) {
//		defined menu list
		List<SysMenuEntity> menuList;
//		if super admin
		if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
//			get all menu list
			menuList = baseMapper.getMenuList(type);
		} else {
//			get user menu list
			menuList = baseMapper.getUserMenuList(user.getUserId(), type);
		}
//		build menu list
		return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList));
	}

	/**
	 * get user authority
	 * @param userDetail
	 * @return authority list
	 */
	@Override
	public Set<String> getUserAuthority(UserDetail user) {
//		defined authority list
		List<String> authorityList;
//		if super admin
		if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
//			get all authority list
			authorityList = baseMapper.getAuthorityList();
		} else {
//			get user authority list
			authorityList = baseMapper.getUserAuthorityList(user.getUserId());
		}
//		convert to permission set
		Set<String> permsSet = new HashSet<>();
		for (String authority : authorityList) {
			if (StrUtil.isBlank(authority)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(authority.trim().split(",")));
		}
//		return permission set
		return permsSet;
	}

}