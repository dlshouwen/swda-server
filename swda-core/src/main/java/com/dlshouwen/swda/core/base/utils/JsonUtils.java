package com.dlshouwen.swda.core.base.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

/**
 * json utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class JsonUtils {

	/** object mapper */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * init
	 */
	static {
//		register java time module
		objectMapper.registerModule(new JavaTimeModule());
//		config
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * to json string
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * parse object
	 * @param <T>
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		if (StrUtil.isEmpty(text)) {
			return null;
		}
		try {
			return objectMapper.readValue(text, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * parase object
	 * @param <T>
	 * @param bytes
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
		if (ArrayUtil.isEmpty(bytes)) {
			return null;
		}
		try {
			return objectMapper.readValue(bytes, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * parse object
	 * @param <T>
	 * @param text
	 * @param typeReference
	 * @return
	 */
	public static <T> T parseObject(String text, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(text, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * parase array
	 * @param <T>
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		if (StrUtil.isEmpty(text)) {
			return new ArrayList<>();
		}
		try {
			return objectMapper.readValue(text,
					objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
