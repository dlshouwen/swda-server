package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.convert.SysUserConvert;
import com.dlshouwen.swda.auth.dao.SysUserDao;
import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.auth.service.MobileUserDetailsService;
import com.dlshouwen.swda.auth.service.SysUserDetailsService;

/**
 * mobile user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {

	/** user details service */
	private final SysUserDetailsService sysUserDetailsService;

	/** user mapper */
	private final SysUserDao sysUserDao;

	/**
	 * load user by mobile
	 * @param mobile
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
//		get user by mobile
		SysUserEntity userEntity = sysUserDao.getByMobile(mobile);
//		if user is empty
		if (userEntity == null) {
//			throw username not found exception
			throw new UsernameNotFoundException("手机号或验证码错误");
		}
//		get user details and return
		return sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertDetail(userEntity));
	}

}
