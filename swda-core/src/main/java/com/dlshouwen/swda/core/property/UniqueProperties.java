package com.dlshouwen.swda.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * xss properties
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix="swda")
public class UniqueProperties {
	
	/** enabled */
	private Map<String, String> uniques;
	
}
