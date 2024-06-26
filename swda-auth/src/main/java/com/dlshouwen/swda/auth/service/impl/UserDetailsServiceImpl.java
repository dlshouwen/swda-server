package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.convert.SysUserConvert;
import com.dlshouwen.swda.auth.dao.SysUserDao;
import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.auth.service.SysUserDetailsService;

/**
 * user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	/** user details service */
	private final SysUserDetailsService sysUserDetailsService;

	/** user mapper */
	private final SysUserDao sysUserDao;

	/**
	 * load user by username
	 * @param username
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		get user by username
		SysUserEntity userEntity = sysUserDao.getByUsername(username);
//		if user is empty
		if (userEntity == null) {
//			throw exception
			throw new UsernameNotFoundException("用户名或密码错误");
		}
//		convert user to user detail and get user details for return
		return sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertDetail(userEntity));
	}

}
