package com.dlshouwen.swda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * redis config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {
	
	/**
	 * generic jackson to json redis serializer
	 * @return
	 */
	public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
//		defined object mapper
		ObjectMapper objectMapper = new ObjectMapper();
//		disable write date as timestamps
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		register time module
		objectMapper.registerModule(new JavaTimeModule());
//		activate default typing
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//		return new serializer
		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}

	/**
	 * redis template
	 * @param factory
	 * @return redis template
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//		defined redis template
		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		set key / hash key serializer by string
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
//		set value / hash value serializer by json
		template.setValueSerializer(genericJackson2JsonRedisSerializer());
		template.setHashValueSerializer(genericJackson2JsonRedisSerializer());
//		set factory
		template.setConnectionFactory(factory);
//		after properties set
		template.afterPropertiesSet();
//		return redis template
		return template;
	}

}
