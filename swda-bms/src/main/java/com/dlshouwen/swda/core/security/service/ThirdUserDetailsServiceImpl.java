package com.dlshouwen.swda.core.security.service;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.base.service.IAuthService;
import com.dlshouwen.swda.bms.permission.convert.UserConvert;
import com.dlshouwen.swda.bms.permission.entity.Organ;
import com.dlshouwen.swda.bms.permission.entity.User;
import com.dlshouwen.swda.bms.permission.mapper.UserMapper;
import com.dlshouwen.swda.bms.permission.service.IOrganService;
import com.dlshouwen.swda.bms.permission.service.IPostService;
import com.dlshouwen.swda.bms.permission.service.IUserPostService;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.log.dict.LoginType;
import com.dlshouwen.swda.core.security.account.UserDetailsService;
import com.dlshouwen.swda.core.security.third.ThirdUserDetailsService;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * third user details service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

	/** user details service */
	private final UserDetailsService userDetailsService;
	
	/** organ service */
	private final IOrganService organService;
	
	/** post service */
	private final IPostService postService;
	
	/** user post service */
	private final IUserPostService userPostService;

	/** third login service */
	private final IAuthService authService;

	/** user mapper */
	private final UserMapper userMapper;

	/**
	 * load user by open type and open id
	 * @param openType
	 * @param openId
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByOpenTypeAndOpenId(String openType, String openId) throws UsernameNotFoundException {
//		defined user id
		Long userId;
//		try catch
		try {
//			get user id by open type and open id
			userId = authService.getUserIdByOpenTypeAndOpenId(openType, openId);
		}catch(SwdaException e) {
//			throw exception
			throw new UsernameNotFoundException("未绑定用户");
		}
//		get user
		User user = userMapper.getUserById(userId);
//		if user is empty
		if (user == null) {
//			throw exception
			throw new UsernameNotFoundException("绑定的系统用户不存在");
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
//		set login type, open type, open id
		userDetail.setLoginType(LoginType.THIRD);
		userDetail.setOpenType(openType);
		userDetail.setOpenId(openId);
//		convert user to get user details for return
		return userDetailsService.getUserDetails(userDetail);
	}

}
