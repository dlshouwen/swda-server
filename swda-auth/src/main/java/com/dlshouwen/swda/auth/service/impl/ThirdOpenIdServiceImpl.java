package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.auth.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.auth.third.ThirdLogin;
import com.dlshouwen.swda.auth.third.ThirdOpenIdService;
import com.dlshouwen.swda.core.exception.SwdaException;

/**
 * third open id service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class ThirdOpenIdServiceImpl implements ThirdOpenIdService {
	
	/** third login config service */
	private final SysThirdLoginConfigService sysThirdLoginConfigService;

	/**
	 * get open id
	 * @param thirdLogin
	 * @return open id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOpenId(ThirdLogin login) {
//		get auth reqeust
		AuthRequest authRequest = sysThirdLoginConfigService.getAuthRequest(login.getOpenType());
//		get call back
		AuthCallback callback = AuthCallback.builder().code(login.getCode()).state(login.getState()).build();
//		do login
		AuthResponse<AuthUser> response = authRequest.login(callback);
//		if not success
		if (!response.ok()) {
//			throw exception
			throw new SwdaException("第三方登录失败");
		}
//		get open id to renturn
		return response.getData().getUuid();
	}

}
