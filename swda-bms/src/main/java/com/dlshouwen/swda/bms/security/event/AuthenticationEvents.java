package com.dlshouwen.swda.bms.security.event;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.core.log.dict.CallResult;
import com.dlshouwen.swda.core.log.dict.LoginStatus;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * authentication events
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
//		loginLogService.saveLoginLog(user.getUsername(), CallResult.SUCCESS, LoginStatus.SUCCESS);
	}

	/**
	 * on failure
	 * @param event
	 */
	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent event) {
//		get user name
		String username = (String) event.getAuthentication().getPrincipal();
//		save login log
//		loginLogService.saveLoginLog(username, CallResult.FAILURE, LoginStatus.PASSWORD_ERROR);
	}

}