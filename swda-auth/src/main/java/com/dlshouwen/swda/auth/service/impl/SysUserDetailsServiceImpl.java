package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.dao.SysRoleDao;
import com.dlshouwen.swda.auth.dao.SysRoleDataScopeDao;
import com.dlshouwen.swda.auth.enums.DataScopeEnum;
import com.dlshouwen.swda.auth.service.SysMenuService;
import com.dlshouwen.swda.auth.service.SysOrgService;
import com.dlshouwen.swda.auth.service.SysUserDetailsService;
import com.dlshouwen.swda.core.dict.OpenClose;
import com.dlshouwen.swda.core.entity.auth.UserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysUserDetailsServiceImpl implements SysUserDetailsService {
	
	/** menu service */
	private final SysMenuService sysMenuService;
	
	/** organ service */
	private final SysOrgService sysOrgService;
	
	/** role mapper */
	private final SysRoleDao sysRoleDao;
	
	/** role data scope mapper */
	private final SysRoleDataScopeDao sysRoleDataScopeDao;

	/**
	 * get user details
	 * @param user detail
	 * @return user details
	 */
	@Override
	public UserDetails getUserDetails(UserDetail userDetail) {
//		if user detail is closed
		// 账号不可用
		if (userDetail.getStatus() == OpenClose.CLOSE) {
//			set enabled false
			userDetail.setEnabled(false);
		}
//		set data scope list
		List<Long> dataScopeList = getDataScope(userDetail);
		userDetail.setDataScopeList(dataScopeList);
//		get authority set
		Set<String> authoritySet = sysMenuService.getUserAuthority(userDetail);
//		get role code list
		List<String> roleCodeList = sysRoleDao.getRoleCodeByUserId(userDetail.getUserId());
//		set role code to authority set
		roleCodeList.forEach(roleCode -> authoritySet.add("ROLE_" + roleCode));
//		set authority set
		userDetail.setAuthoritySet(authoritySet);
//		return user detail
		return userDetail;
	}

	/**
	 * get data scope
	 * @param userDetail
	 * @return data scopes
	 */
	private List<Long> getDataScope(UserDetail userDetail) {
//		get data scope by user id
		Integer dataScope = sysRoleDao.getDataScopeByUserId(userDetail.getUserId());
//		if data scope is empty return empty list
		if (dataScope == null) {
			return new ArrayList<>();
		}
//		all data scope
		if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
//			return null
			return null;
		} else if (dataScope.equals(DataScopeEnum.ORG_AND_CHILD.getValue())) {
//			get sub organ id list
			List<Long> dataScopeList = sysOrgService.getSubOrgIdList(userDetail.getOrganId());
//			add self data scope
			dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getUserId()));
//			return data scope list
			return dataScopeList;
		} else if (dataScope.equals(DataScopeEnum.ORG_ONLY.getValue())) {
//			create data scope list
			List<Long> dataScopeList = new ArrayList<>();
//			add self organ
			dataScopeList.add(userDetail.getOrganId());
//			add self data scope
			dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getUserId()));
//			return data scope list
			return dataScopeList;
		} else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
//			return self data scope
			return sysRoleDataScopeDao.getDataScopeList(userDetail.getUserId());
		}
//		return empty list
		return new ArrayList<>();
	}

}
