package com.dlshouwen.swda.config;

import com.dlshouwen.swda.utils.ObjectMapperUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * jackson config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
public class JacksonConfig implements WebMvcConfigurer {

	/**
	 * pattern
	 */
	@Value("${spring.jackson.date-format}")
	private String pattern;

	/**
	 * tome zone
	 */
	@Value("${spring.jackson.time-zone}")
	private String timeZone;

	/**
	 * extend message converters
	 * @param converters
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		get object mapper from converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = converter.getObjectMapper();
//		set long / bitint to string for fix loss of precision 
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
//		set date format
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setDateFormat(new SimpleDateFormat(pattern));
		objectMapper.setTimeZone(TimeZone.getTimeZone(timeZone));
//		set object mapper
		converter.setObjectMapper(objectMapper);
		ObjectMapperUtils.mapper = objectMapper;
//		add converter
		converters.add(0, converter);
	}

}