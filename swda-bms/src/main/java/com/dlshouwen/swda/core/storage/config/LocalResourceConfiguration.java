package com.dlshouwen.swda.core.storage.config;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dlshouwen.swda.core.storage.enums.StorageTypeEnum;
import com.dlshouwen.swda.core.storage.properties.LocalStorageProperties;
import com.dlshouwen.swda.core.storage.properties.StorageProperties;

/**
 * local resource configuration
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "storage", value = "enabled")
public class LocalResourceConfiguration implements WebMvcConfigurer {
	
	/** sotrage properties */
	@Resource
	private StorageProperties properties;

	/**
	 * add resource handlers
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		if not local storage type then return
		if (properties.getConfig().getType() != StorageTypeEnum.LOCAL) {
			return;
		}
//		get local
		LocalStorageProperties local = properties.getLocal();
//		add resource handler
		registry.addResourceHandler("/" + local.getUrl() + "/**").addResourceLocations("file:" + local.getPath() + "/");
	}

}