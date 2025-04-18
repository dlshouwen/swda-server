package com.dlshouwen.swda.core.security.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.core.security.mobile.MobileVerifyCodeService;

/**
 * mobile verify code service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
	
	/** sms api */
	private final SmsApi smsApi;

	/**
	 * verify code
	 * @param mobile
	 * @param code
	 * @return is success
	 */
	@Override
	public boolean verifyCode(String mobile, String code) {
		return smsApi.verifyCode(mobile, code);
	}

}
