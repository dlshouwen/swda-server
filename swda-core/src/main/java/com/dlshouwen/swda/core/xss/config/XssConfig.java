package com.dlshouwen.swda.core.xss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.dlshouwen.swda.core.xss.filter.XssFilter;
import com.dlshouwen.swda.core.xss.filter.XssFilterJsonDeserializer;
import com.dlshouwen.swda.core.xss.properties.XssProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * xss config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(prefix = "swda.xss", value = "enabled")
public class XssConfig {

	/** path matcher */
	private final static PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * xss filter
	 * @param properties
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties) {
//		get bean
		FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<>();
//		set filter, order, name
		bean.setFilter(new XssFilter(properties, pathMatcher));
		bean.setOrder(Integer.MAX_VALUE);
		bean.setName("xssFilter");
//		return bean
		return bean;
	}

	/**
	 * xss filter object mapper
	 * @param builder
	 * @param properties
	 * @return
	 */
	@Bean
	public ObjectMapper xssFilterObjectMapper(Jackson2ObjectMapperBuilder builder, XssProperties properties) {
//		build object mapper
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//		register xss module
		SimpleModule module = new SimpleModule("XssFilterJsonDeserializer");
		module.addDeserializer(String.class, new XssFilterJsonDeserializer(properties, pathMatcher));
		objectMapper.registerModule(module);
//		return object mapper
		return objectMapper;
	}

}
