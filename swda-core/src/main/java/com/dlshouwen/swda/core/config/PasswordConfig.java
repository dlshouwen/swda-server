package com.dlshouwen.swda.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dlshouwen.swda.core.handler.Sm3PasswordEncoder;

/**
 * password config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
public class PasswordConfig {

	/**
	 * password encoder
	 * @return password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return sm3 password encoder
		return new Sm3PasswordEncoder();
	}

}
