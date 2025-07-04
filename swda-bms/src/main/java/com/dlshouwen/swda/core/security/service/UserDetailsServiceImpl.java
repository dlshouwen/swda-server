package com.dlshouwen.swda.core.security.service;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.permission.convert.UserConvert;
import com.dlshouwen.swda.bms.permission.dict.DataScopeType;
import com.dlshouwen.swda.bms.permission.entity.Organ;
import com.dlshouwen.swda.bms.permission.entity.User;
import com.dlshouwen.swda.bms.permission.mapper.RoleMapper;
import com.dlshouwen.swda.bms.permission.mapper.RoleOrganMapper;
import com.dlshouwen.swda.bms.permission.mapper.UserMapper;
import com.dlshouwen.swda.bms.permission.service.IOrganService;
import com.dlshouwen.swda.bms.permission.service.IPostService;
import com.dlshouwen.swda.bms.permission.service.IUserPostService;
import com.dlshouwen.swda.bms.system.service.IMenuService;
import com.dlshouwen.swda.core.log.dict.LoginType;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * user details service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, com.dlshouwen.swda.core.security.account.UserDetailsService {
	
	/** menu service */
	private final IMenuService menuService;
	
	/** organ service */
	private final IOrganService organService;
	
	/** post service */
	private final IPostService postService;
	
	/** user post service */
	private final IUserPostService userPostService;
	
	/** role mapper */
	private final RoleMapper roleMapper;
	
	/** role data scope mapper */
	private final RoleOrganMapper roleOrganMapper;
	
	/** user mapper */
	private final UserMapper userMapper;
	
	/**
	 * load user by username
	 * @param username
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		get user by username
		User user = userMapper.getUserByUsername(username);
//		if user is empty
		if (user == null) {
//			throw exception
			throw new UsernameNotFoundException("用户名或密码错误");
		}
//		convert to user detail
		UserDetail userDetail = UserConvert.INSTANCE.convert2Detail(user);
//		get organ
		Organ organ = organService.getById(user.getOrganId());
//		if organ is not empty
		if(organ != null) {
//			set organ name
			userDetail.setOrganName(organ.getOrganName());
		}
//		get post id list
		List<Long> postIdList = userPostService.getPostIdList(user.getUserId());
//		set post id list
		userDetail.setPostIdList(postIdList);
//		if post id list not empty
		if(postIdList!=null&&postIdList.size()>0) {
//			get post name list
			List<String> postNameList = postService.getPostNameList(postIdList);
//			set post name list
			userDetail.setPostNameList(postNameList);
		}
//		set login type
		userDetail.setLoginType(LoginType.ACCOUNT);
//		convert user to user detail and get user details for return
		return this.getUserDetails(userDetail);
	}

	/**
	 * get user details
	 * @param userDetail
	 * @return user details
	 */
	@Override
	public UserDetails getUserDetails(UserDetail userDetail) {
//		get data scope set to user detail
		List<Long> dataScopeList = getDataScope(userDetail);
		userDetail.setDataScopeList(dataScopeList);
//		get login user authority
		Set<String> authoritySet = menuService.getLoginUserAuthorityList(userDetail);
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
		String dataScope = roleMapper.getDataScopeByUserId(userDetail.getUserId());
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
