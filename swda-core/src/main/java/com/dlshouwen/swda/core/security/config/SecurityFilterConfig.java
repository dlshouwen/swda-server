package com.dlshouwen.swda.core.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dlshouwen.swda.core.security.exception.SecurityAuthenticationEntryPoint;

import java.util.List;

/**
 * security filter config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {
	
	/** authentication token filter */
	private final OncePerRequestFilter authenticationTokenFilter;
	
	/** security permit resource */
	private final SecurityPermitResource securityPermitResource;

	/**
	 * security filter chain
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		get permit list
		List<String> permitList = securityPermitResource.getPermitList();
//		permit list to array
		String[] permits = permitList.toArray(new String[0]);
//		add filter before
		http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers(permits).permitAll()
				.requestMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()))
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.csrf(AbstractHttpConfigurer::disable);
//		build http
		return http.build();
	}

}
