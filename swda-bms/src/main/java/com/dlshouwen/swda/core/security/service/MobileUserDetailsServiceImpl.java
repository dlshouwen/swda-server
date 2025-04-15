package com.dlshouwen.swda.core.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.permission.convert.UserConvert;
import com.dlshouwen.swda.bms.permission.entity.User;
import com.dlshouwen.swda.bms.permission.mapper.UserMapper;
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
//		set login type
		userDetail.setLoginType(LoginType.MOBILE);
//		get user details and return
		return userDetailsService.getUserDetails(userDetail);
	}

}
