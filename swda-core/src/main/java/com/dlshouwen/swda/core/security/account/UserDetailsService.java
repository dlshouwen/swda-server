package com.dlshouwen.swda.core.security.account;

import org.springframework.security.core.userdetails.UserDetails;

import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * user details service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface UserDetailsService {

	/**
	 * get user details
	 * @param userDetail
	 * @return user details
	 */
	UserDetails getUserDetails(UserDetail userDetail);

}
