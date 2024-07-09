package com.dlshouwen.swda.bms.service.impl;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.dict.DataScopeType;
import com.dlshouwen.swda.bms.mapper.RoleMapper;
import com.dlshouwen.swda.bms.mapper.RoleOrganMapper;
import com.dlshouwen.swda.bms.service.IPermissionService;
import com.dlshouwen.swda.bms.service.IOrganService;
import com.dlshouwen.swda.bms.service.IUserDetailsService;
import com.dlshouwen.swda.core.dict.OpenClose;
import com.dlshouwen.swda.core.entity.auth.UserDetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
public class UserDetailsServiceImpl implements IUserDetailsService {
	
	/** permission service */
	private final IPermissionService permissionService;
	
	/** organ service */
	private final IOrganService organService;
	
	/** role mapper */
	private final RoleMapper roleMapper;
	
	/** role data scope mapper */
	private final RoleOrganMapper roleOrganMapper;

	/**
	 * get user details
	 * @param userDetail
	 * @return user details
	 */
	@Override
	public UserDetails getUserDetails(UserDetail userDetail) {
//		if user disabled
		if (userDetail.getStatus() == OpenClose.CLOSE) {
//			set enabled false
			userDetail.setEnabled(false);
		}
//		get data scope set to user detail
		List<Long> dataScopeList = getDataScope(userDetail);
		userDetail.setDataScopeList(dataScopeList);
//		get user authority
		Set<String> authoritySet = permissionService.getUserAuthorityList(userDetail);
//		get role code by user id
		List<String> roleCodeList = roleMapper.geRoleCodeByUserId(userDetail.getUserId());
//		set role code to authority set
		roleCodeList.forEach(roleCode -> authoritySet.add("ROLE_" + roleCode));
//		set authority set
		userDetail.setAuthoritySet(authoritySet);
//		return user details
		return userDetail;
	}

	/**
	 * get data scope
	 * @param userDetail
	 * @return data scope
	 */
	private List<Long> getDataScope(UserDetail userDetail) {
//		get data scope by user id
		Integer dataScope = roleMapper.getDataScopeByUserId(userDetail.getUserId());
//		if data scope is null
		if (dataScope == null) {
//			return empty list
			return new ArrayList<>();
		}
//		if all premission
		if (dataScope.equals(DataScopeType.ALL)) {
//			return null
			return null;
		} else if (dataScope.equals(DataScopeType.ORGAN_AND_CHILDREN)) {
//			get sub organ id list
			List<Long> dataScopeList = organService.getSubOrganIdList(userDetail.getOrganId());
//			add user data scope list
			dataScopeList.addAll(roleOrganMapper.getDataScopeList(userDetail.getUserId()));
//			return data scope list
			return dataScopeList;
		} else if (dataScope.equals(DataScopeType.ORGAN_ONLY)) {
//			create data scope list
			List<Long> dataScopeList = new ArrayList<>();
//			add self organ id
			dataScopeList.add(userDetail.getOrganId());
//			add user data scope list
			dataScopeList.addAll(roleOrganMapper.getDataScopeList(userDetail.getUserId()));
//			return data scope list
			return dataScopeList;
		} else if (dataScope.equals(DataScopeType.CUSTOM)) {
//			add user data scope list
			return roleOrganMapper.getDataScopeList(userDetail.getUserId());
		}
//		return empty list
		return new ArrayList<>();
	}

}
