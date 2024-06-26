package com.dlshouwen.swda.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dlshouwen.swda.auth.mobile.MobileAuthenticationProvider;
import com.dlshouwen.swda.auth.service.MobileUserDetailsService;
import com.dlshouwen.swda.auth.service.MobileVerifyCodeService;
import com.dlshouwen.swda.auth.third.ThirdAuthenticationProvider;
import com.dlshouwen.swda.auth.third.ThirdOpenIdService;
import com.dlshouwen.swda.auth.third.ThirdUserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * security config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	/** user details service */
	private final UserDetailsService userDetailsService;

	/** mobile user details service */
	private final MobileUserDetailsService mobileUserDetailsService;
	/** mobile verify code service */
	private final MobileVerifyCodeService mobileVerifyCodeService;

	/** third user details service */
	private final ThirdUserDetailsService thirdUserDetailsService;
	/** third open id service */
	private final ThirdOpenIdService thirdOpenIdService;

	/** password encoder */
	private final PasswordEncoder passwordEncoder;
	/** application event publisher */
	private final ApplicationEventPublisher applicationEventPublisher;

	/**
	 * dao authentication provider
	 * @return dao authentication provider
	 */
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
//		create dao authentication provider
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		set password encoder, user details service
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//		return dao authentication provider
		return daoAuthenticationProvider;
	}

	/**
	 * mobile authentication provider
	 * @return mobile authentication provider
	 */
	@Bean
	MobileAuthenticationProvider mobileAuthenticationProvider() {
//		create and return mobile authentication provider
		return new MobileAuthenticationProvider(mobileUserDetailsService, mobileVerifyCodeService);
	}

	/**
	 * third authentication provider
	 * @return third authentication provider
	 */
	@Bean
	ThirdAuthenticationProvider thirdAuthenticationProvider() {
//		create and return third authentication provider
		return new ThirdAuthenticationProvider(thirdUserDetailsService, thirdOpenIdService);
	}

	/**
	 * authentication manager
	 * @return authentication manager
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
//		create provider list
		List<AuthenticationProvider> providerList = new ArrayList<>();
//		add dao, mobile third authentcation provider
		providerList.add(daoAuthenticationProvider());
		providerList.add(mobileAuthenticationProvider());
		providerList.add(thirdAuthenticationProvider());
//		create provider manager
		ProviderManager providerManager = new ProviderManager(providerList);
//		set authencation event publisher
		providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));
//		return provider manager
		return providerManager;
	}

}
