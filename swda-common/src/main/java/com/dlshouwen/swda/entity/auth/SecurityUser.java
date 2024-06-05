package com.dlshouwen.swda.entity.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUser {
	
    /**
     * 获取用户信息
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
     * 获取用户ID
     */
    public static Long getUserId() {
        UserDetail user = getUser();
        return user==null?null:user.getUserId();
    }

}
