package com.dlshouwen.swda.core.security.mobile;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * mobile user details service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface MobileUserDetailsService {

	/**
	 * laod user by mobile
	 * @param mobile
	 * @return user details
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;

}
