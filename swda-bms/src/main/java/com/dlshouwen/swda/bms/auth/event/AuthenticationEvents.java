package com.dlshouwen.swda.bms.auth.event;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.core.common.dict.CallResult;
import com.dlshouwen.swda.core.common.dict.LoginStatus;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * 认证事件处理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Component
@AllArgsConstructor
public class AuthenticationEvents {
    private final ILoginLogService loginLogService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();

        // 保存登录日志
        loginLogService.saveLoginLog(user.getUsername(), CallResult.SUCCESS, LoginStatus.SUCCESS);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 用户名
        String username = (String) event.getAuthentication().getPrincipal();
        // 保存登录日志
        loginLogService.saveLoginLog(username, CallResult.FAILURE, LoginStatus.PASSWORD_ERROR);
    }

}