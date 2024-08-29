package com.dlshouwen.swda.bms.security.event;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.security.exception.AccountUserNotFoundException;
import com.dlshouwen.swda.bms.security.exception.MobileUserNotFoundException;
import com.dlshouwen.swda.bms.security.exception.ThirdUnbindException;
import com.dlshouwen.swda.bms.security.exception.ThirdUserNotFoundException;
import com.dlshouwen.swda.core.log.dict.LoginStatus;
import com.dlshouwen.swda.core.log.dict.LoginType;
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
		Long loginLogId = loginLogService.saveLoginLog(user.getLoginType(), LoginStatus.SUCCESS, "", user.getUsername(), user.getMobile(), user.getOpenType(), user.getOpenId());
//		set login log id
		user.setLoginLogId(loginLogId);
	}

	/**
	 * on failure
	 * @param event
	 */
	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent event) {
//		get principal
		String principal = (String) event.getAuthentication().getPrincipal();
//		account user not found exception
		if(event.getException() instanceof AccountUserNotFoundException) {
//			save login log
			loginLogService.saveLoginLog(LoginType.ACCOUNT, LoginStatus.FAILURE, "用户不存在", principal, null, null, null);
		}
//		account bad credentials exception
		if(event.getException() instanceof BadCredentialsException) {
//			save login log
			loginLogService.saveLoginLog(LoginType.ACCOUNT, LoginStatus.FAILURE, "密码错误", principal, null, null, null);
		}
//		mobile user not found exception
		if(event.getException() instanceof MobileUserNotFoundException) {
//			save login log
			loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.FAILURE, "用户不存在", principal, null, null, null);
		}
//		third unbind exception
		if(event.getException() instanceof ThirdUnbindException) {
//			save login log
			loginLogService.saveLoginLog(LoginType.THIRD, LoginStatus.FAILURE, "未绑定用户", principal, null, null, null);
		}
//		third user not found exception
		if(event.getException() instanceof ThirdUserNotFoundException) {
//			save login log
			loginLogService.saveLoginLog(LoginType.MOBILE, LoginStatus.FAILURE, "绑定的用户不存在", principal, null, null, null);
		}
	}

}
