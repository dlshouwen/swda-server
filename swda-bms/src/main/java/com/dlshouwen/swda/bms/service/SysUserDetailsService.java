package com.dlshouwen.swda.bms.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.dlshouwen.swda.core.entity.auth.UserDetail;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysUserDetailsService {

	/**
	 * 获取 UserDetails 对象，设置用户权限信息
	 */
	UserDetails getUserDetails(UserDetail userDetail);
}
