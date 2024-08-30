package com.dlshouwen.swda.core.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * token properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swda.token")
public class TokenProperties {

	/** access token expire */
	private int accessTokenExpire = 60 * 60 * 2;

	/** refresh token expire */
	private int refreshTokenExpire = 60 * 60 * 24 * 14;

}