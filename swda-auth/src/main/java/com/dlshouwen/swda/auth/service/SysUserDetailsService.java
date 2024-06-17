package com.dlshouwen.swda.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.dlshouwen.swda.core.entity.auth.UserDetail;

public interface SysUserDetailsService {

    /**
     * 获取 UserDetails 对象，设置用户权限信息
     */
    UserDetails getUserDetails(UserDetail userDetail);
}
