package com.dlshouwen.swda.bms.security.mobile;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.system.convert.UserConvert;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;
import com.dlshouwen.swda.core.security.account.UserDetailsService;
import com.dlshouwen.swda.core.security.mobile.MobileUserDetailsService;

/**
 * mobile user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
//			throw username not found exception
			throw new UsernameNotFoundException("手机号或验证码错误");
		}
//		get user details and return
		return userDetailsService.getUserDetails(UserConvert.INSTANCE.convert2Detail(user));
	}

}