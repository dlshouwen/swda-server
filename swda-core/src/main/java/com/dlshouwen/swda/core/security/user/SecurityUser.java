package com.dlshouwen.swda.core.security.user;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * security user
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class SecurityUser {
	
	/**
	 * get user
	 * @return user detail
	 */
	public static UserDetail getUser() {
		UserDetail user;
		try {
			user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		return user;
	}

	/**
	 * get user id
	 * @return user id
	 */
	public static Long getUserId() {
		UserDetail user = getUser();
		return user==null?null:user.getUserId();
	}

}
