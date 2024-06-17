package com.dlshouwen.swda.core.config;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * security permit resource
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Component
public class SecurityPermitResource {
	
	/**
	 * get permit list
	 */
	@SneakyThrows
	public List<String> getPermitList() {
//		get auth.yml -> ignore urls
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:auth.yml");
		String key = "auth.ignore_urls";
//		get properties list
		return getPropertiesList(key, resources);
	}

	/**
	 * get properties list
	 * @param key
	 * @param resources
	 * @return
	 */
	private List<String> getPropertiesList(String key, Resource... resources) {
//		defined property list
		List<String> propertyList = new ArrayList<>();
//		for each resource
		for (Resource resource : resources) {
//			get properties
			Properties properties = loadYamlProperties(resource);
//			for each entry
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
//				get key
				String _key = StringUtils.substringBefore(entry.getKey().toString(), "[");
//				add property
				if (_key.equalsIgnoreCase(key)) {
					propertyList.add(entry.getValue().toString());
				}
			}
		}
//		return property list
		return propertyList;
	}

	/**
	 * load yml properties
	 * @param resources
	 * @return
	 */
	private Properties loadYamlProperties(Resource... resources) {
//		get factory
		YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
//		set resources
		factory.setResources(resources);
//		after properties set
		factory.afterPropertiesSet();
//		get properties
		return factory.getObject();
	}
	
}
