package com.dlshouwen.swda.core.security.third;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * third user details service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ThirdUserDetailsService {

	/**
	 * load user by open type and open id
	 * @param openType
	 * @param openId
	 * @return user details
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserByOpenTypeAndOpenId(String openType, String openId) throws UsernameNotFoundException;

}
