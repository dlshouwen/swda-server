package com.dlshouwen.swda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
public class RedisConfig {

	/**
	 * config redis template
	 * @param connectionFactory
	 * @return redis template
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//		defined redis template
		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		set connection factory
		template.setConnectionFactory(connectionFactory);
//		set value serializer
//		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		serializer.setObjectMapper(mapper);
//		template.setValueSerializer(serializer);
//		set key serializer
		template.setKeySerializer(new StringRedisSerializer());
//		set hash serializer
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
//		after properties set
		template.afterPropertiesSet();
//		return
		return template;
	}

}
