package com.dlshouwen.swda.bms.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.dlshouwen.swda.core.entity.auth.UserDetail;

/**
 * user details service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IUserDetailsService {

	/**
	 * get user details
	 * @param userDetail
	 * @return user details
	 */
	UserDetails getUserDetails(UserDetail userDetail);

}
