package com.dlshouwen.swda.bms.security.third;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.auth.service.IAuthService;
import com.dlshouwen.swda.bms.security.exception.ThirdUnbindException;
import com.dlshouwen.swda.bms.security.exception.ThirdUserNotFoundException;
import com.dlshouwen.swda.bms.system.convert.UserConvert;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.security.account.UserDetailsService;
import com.dlshouwen.swda.core.security.third.ThirdUserDetailsService;

/**
 * third user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

	/** user details service */
	private final UserDetailsService userDetailsService;

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
//		defined user id
		Long userId;
//		try catch
		try {
//			get user id by open type and open id
			userId = authService.getUserIdByOpenTypeAndOpenId(openType, openId);
		}catch(SwdaException e) {
//			throw exception
			throw new ThirdUnbindException("未绑定用户");
		}
//		get user
		User user = userMapper.getUserById(userId);
//		if user is empty
		if (user == null) {
//			throw exception
			throw new ThirdUserNotFoundException("绑定的系统用户不存在");
		}
//		convert user to get user details for return
		return userDetailsService.getUserDetails(UserConvert.INSTANCE.convert2Detail(user));
	}

}
