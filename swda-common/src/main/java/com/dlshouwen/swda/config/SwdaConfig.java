package com.dlshouwen.swda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * swda config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class SwdaConfig {

	/** key */
	public static String KEY = "SWDA@dl0";
	
	/** div scale */
	public static int DIV_SCALE = 10;;

	/** token expiration */
	public static int TOKEN_EXPIRATION = 60*60*1000;;
	
	/** token signkey */
	public static String TOKEN_SIGNKEY = "xxksd-$.2039#--0f";
	
	/** redis permission prefix */
	public static String REDIS_PERMISSION_PREFIX = "user_permission_";
	
	/** zip buffer */
	public static int ZIP_BUFFER = 2*1024;

	/**
	 * inject value
	 */
	@Value("${swda.key}")
	public String key;
	@Value("${swda.scale.div}")
	public Integer div_scale;
	@Value("${swda.token.expiration}")
	public Integer token_expiration;
	@Value("${swda.token.signkey}")
	public String token_signkey;
	@Value("${swda.redis.permission.prefix}")
	public String redis_permission_prefix;
	@Value("${swda.zip.buffer}")
	public Integer zip_buffer;

	/**
	 * init to load
	 */
	@PostConstruct
	public void init() {
//		set value
		KEY = key==null?KEY:key;
		DIV_SCALE = div_scale==null?DIV_SCALE:div_scale;
		TOKEN_EXPIRATION = token_expiration==null?TOKEN_EXPIRATION:token_expiration;
		TOKEN_SIGNKEY = token_signkey==null?TOKEN_SIGNKEY:token_signkey;
		REDIS_PERMISSION_PREFIX = redis_permission_prefix==null?REDIS_PERMISSION_PREFIX:redis_permission_prefix;
		ZIP_BUFFER = zip_buffer==null?ZIP_BUFFER:zip_buffer;
	}

}