package com.dlshouwen.swda.bms.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.third.ThirdUserDetailsService;
import com.dlshouwen.swda.bms.auth.service.IAuthService;
import com.dlshouwen.swda.bms.auth.service.IUserDetailsService;
import com.dlshouwen.swda.bms.system.convert.UserConvert;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;

/**
 * third user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

	/** user details service */
	private final IUserDetailsService userDetailsService;

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
	public UserDetails loadUserByOpenTypeAndOpenId(Integer openType, String openId) throws UsernameNotFoundException {
//		get user id by open type and open id
		Long userId = authService.getUserIdByOpenTypeAndOpenId(openType, openId);
//		get user
		User user = userMapper.getUserById(userId);
//		if user is empty
		if (user == null) {
//			throw exception
			throw new UsernameNotFoundException("绑定的系统用户，不存在");
		}
//		convert user to get user details for return
		return userDetailsService.getUserDetails(UserConvert.INSTANCE.convert2Detail(user));
	}

}