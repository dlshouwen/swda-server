package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.convert.SysUserConvert;
import com.dlshouwen.swda.auth.dao.SysUserDao;
import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.auth.service.SysThirdLoginService;
import com.dlshouwen.swda.auth.service.SysUserDetailsService;
import com.dlshouwen.swda.auth.third.ThirdUserDetailsService;

/**
 * third user details service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

	/** user details service */
	private final SysUserDetailsService sysUserDetailsService;

	/** third login service */
	private final SysThirdLoginService sysThirdLoginService;

	/** user mapper */
	private final SysUserDao sysUserDao;

	/**
	 * load user by open type and open id
	 * @param openType
	 * @param openId
	 * @return user details
	 */
	@Override
	public UserDetails loadUserByOpenTypeAndOpenId(String openType, String openId) throws UsernameNotFoundException {
//		get user id by open type and open id
		Long userId = sysThirdLoginService.getUserIdByOpenTypeAndOpenId(openType, openId);
//		get user
		SysUserEntity userEntity = sysUserDao.getById(userId);
//		if user is empty
		if (userEntity == null) {
//			throw exception
			throw new UsernameNotFoundException("绑定的系统用户，不存在");
		}
//		convert user to get user details for return
		return sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertDetail(userEntity));
	}
}
