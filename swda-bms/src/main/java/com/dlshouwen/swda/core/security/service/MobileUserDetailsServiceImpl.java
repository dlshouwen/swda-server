package com.dlshouwen.swda.core.security.service;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.permission.convert.UserConvert;
import com.dlshouwen.swda.bms.permission.entity.Organ;
import com.dlshouwen.swda.bms.permission.entity.User;
import com.dlshouwen.swda.bms.permission.mapper.UserMapper;
import com.dlshouwen.swda.bms.permission.service.IOrganService;
import com.dlshouwen.swda.bms.permission.service.IPostService;
import com.dlshouwen.swda.bms.permission.service.IUserPostService;
import com.dlshouwen.swda.core.log.dict.LoginType;
import com.dlshouwen.swda.core.security.account.UserDetailsService;
import com.dlshouwen.swda.core.security.mobile.MobileUserDetailsService;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * mobile user details service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {

	/** user details service */
	private final UserDetailsService userDetailsService;
	
	/** organ service */
	private final IOrganService organService;
	
	/** post service */
	private final IPostService postService;
	
	/** user post service */
	private final IUserPostService userPostService;

	/** user mapper */
	private final UserMapper userMapper;

	/**
	 * load user by mobile
	 * @param mobile
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
//		get user by mobile
		User user = userMapper.getUserByMobile(mobile);
//		if user is empty
		if (user == null) {
//			throw mobile user not found exception
			throw new UsernameNotFoundException("手机号或验证码错误");
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
		userDetail.setLoginType(LoginType.MOBILE);
//		get user details and return
		return userDetailsService.getUserDetails(userDetail);
	}

}
