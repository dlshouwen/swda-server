package com.dlshouwen.swda.core.xss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * xss properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "swda.xss")
public class XssProperties {

	/** enabled */
	private boolean enabled;

	/** exclude urls */
	private List<String> excludeUrls = Collections.emptyList();

}
