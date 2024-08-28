package com.dlshouwen.swda.bms.security.third;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.auth.service.IAuthPlatformService;
import com.dlshouwen.swda.bms.security.exception.ThirdLoginException;
import com.dlshouwen.swda.core.security.third.ThirdLogin;
import com.dlshouwen.swda.core.security.third.ThirdOpenIdService;

/**
 * third open id service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdOpenIdServiceImpl implements ThirdOpenIdService {
	
	/** third login config service */
	private final IAuthPlatformService authPlatformService;

	/**
	 * get open id
	 * @param thirdLogin
	 * @return open id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOpenId(ThirdLogin login) {
//		get auth reqeust
		AuthRequest authRequest = authPlatformService.getAuthRequest(login.getOpenType());
//		get call back
		AuthCallback callback = AuthCallback.builder().code(login.getCode()).state(login.getState()).build();
//		do login
		AuthResponse<AuthUser> response = authRequest.login(callback);
//		if not success
		if (!response.ok()) {
//			throw exception
			throw new ThirdLoginException(response.getMsg());
		}
//		get open id to renturn
		return response.getData().getUuid();
	}

}
