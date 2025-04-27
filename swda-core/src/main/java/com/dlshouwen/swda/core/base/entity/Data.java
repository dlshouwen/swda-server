package com.dlshouwen.swda.core.base.entity;

import java.util.HashMap;
import java.util.Map;

import com.dlshouwen.swda.core.base.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * data
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class Data {

	/** version */
	public static String version = "";

	/** attr */
	public static Map<String, String> attr = new HashMap<>();

	/** dict */
	public static Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();

	/** unique */
	public static Map<String, String> unique = new HashMap<String, String>();
	
	/**
	 * get attr
	 * @param attrId
	 * @return
	 */
	public static String getAttr(String attrId) {
		return attr.get(attrId);
	}
	
	/**
	 * get int attr
	 * @param attrId
	 * @return
	 */
	public static int getIntAttr(String attrId) {
		return Integer.parseInt(getAttr(attrId));
	}
	
	/**
	 * get boolean attr
	 * @param attrId
	 * @return
	 */
	public static boolean getBooleanAttr(String attrId) {
		return Boolean.parseBoolean(getAttr(attrId));
	}
	
	/**
	 * get json attr
	 * @param <T>
	 * @param attrId
	 * @param typeReference
	 * @return
	 */
	public static <T> T getJsonAttr(String attrId, TypeReference<T> typeReference) {
		return JsonUtils.parseObject(attrId, typeReference);
	}

}
