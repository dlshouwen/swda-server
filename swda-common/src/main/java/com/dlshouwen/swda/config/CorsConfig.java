package com.dlshouwen.swda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * cors config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
public class CorsConfig {
	
	/**
	 * cors filter
	 * @return cors filter
	 */
	@Bean
	public CorsFilter corsFilter() {
//		defined url based cors config source
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		defined cors config
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
//		set cors info
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedMethod("*");
//		register cors config
		source.registerCorsConfiguration("/**", corsConfiguration);
//		return filter
        return new CorsFilter(source);
	}

}
