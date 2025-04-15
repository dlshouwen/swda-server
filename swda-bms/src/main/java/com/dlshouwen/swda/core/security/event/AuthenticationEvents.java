package com.dlshouwen.swda.core.security.event;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.core.log.dict.LoginStatus;
import com.dlshouwen.swda.core.log.dict.LoginType;
import com.dlshouwen.swda.core.security.mobile.MobileAuthenticationToken;
import com.dlshouwen.swda.core.security.third.ThirdAuthenticationToken;
import com.dlshouwen.swda.core.security.user.UserDetail;

import cn.hutool.json.JSONUtil;

/**
 * authentication events
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Component
@AllArgsConstructor
public class AuthenticationEvents {

	/** login log service */
	private final ILoginLogService loginLogService;

	/**
	 * on success
	 * @param event
	 */
	@EventListener
	public void onSuccess(AuthenticationSuccessEvent event) {
//		get user detail
		UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();
//		save login log
		Long loginLogId = loginLogService.saveLoginLog(user.getLoginType(), LoginStatus.SUCCESS, JSONUtil.toJsonStr(user), "success");
//		set login log id
		user.setLoginLogId(loginLogId);
	}

	/**
	 * on failure
	 * @param event
	 */
	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent event) {
//		defined login status
		Integer loginStatus = LoginStatus.SUCCESS;
//		set login status
		if(event.getException() instanceof UsernameNotFoundException)
			loginStatus = LoginStatus.USER_NOR_FOUND;
		if(event.getException() instanceof BadCredentialsException)
			loginStatus = LoginStatus.BAD_CREDENTIALS;
		if(event.getException() instanceof CredentialsExpiredException)
			loginStatus = LoginStatus.CREDENTIALS_EXPIRED;
		if(event.getException() instanceof DisabledException)
			loginStatus = LoginStatus.USER_DISABLED;
//		defined login type
		Integer loginType = LoginType.ACCOUNT;
//		set login type
		if(event.getAuthentication().getClass() == UsernamePasswordAuthenticationToken.class)
			loginType = LoginType.ACCOUNT;
		if(event.getAuthentication().getClass() == MobileAuthenticationToken.class)
			loginType = LoginType.MOBILE;
		if(event.getAuthentication().getClass() == ThirdAuthenticationToken.class)
			loginType = LoginType.THIRD;
//		save login log
		loginLogService.saveLoginLog(loginType, loginStatus, JSONUtil.toJsonStr(event.getAuthentication().getPrincipal()), event.getException().getMessage());
	}

}
