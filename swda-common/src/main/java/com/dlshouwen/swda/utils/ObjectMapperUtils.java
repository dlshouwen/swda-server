package com.dlshouwen.swda.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * object mapper utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ObjectMapperUtils {

	/** mapper */
	public static ObjectMapper mapper;

	/**
	 * get instance
	 * @return mapper
	 */
	public static ObjectMapper getInstance() {
		return mapper;
	}
	
}