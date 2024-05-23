package com.dlshouwen.swda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * web config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
public class WebConfig {

	/**
	 * config request context listener
	 * <p>to fix RequestContextHolder.getRequestAttributes() is null</p>
	 * @return request context listener
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}

}
