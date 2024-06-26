package com.dlshouwen.swda.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlshouwen.swda.api.module.SmsApi;

import java.util.Map;

/**
 * login config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
public class LoginConfig {

	/**
	 * sms api
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	SmsApi smsApi() {
//		new sms api implement
		return new SmsApi() {
			@Override
			public boolean send(String mobile, Map<String, String> params) {
				return false;
			}

			@Override
			public boolean sendCode(String mobile, String key, String value) {
				return false;
			}

			@Override
			public boolean verifyCode(String mobile, String code) {
				return false;
			}
		};
	}
}
