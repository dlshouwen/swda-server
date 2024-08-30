package com.dlshouwen.swda.core.unique.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * unique properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "swda")
public class UniqueProperties {

	/** enabled */
	private Map<String, String> uniques;

}
