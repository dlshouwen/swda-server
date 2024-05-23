package com.dlshouwen.swda.entity.base;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * data
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class Data {

	/** version */
	public static String version = "";

	/** attr */
	public static Map<String, String> attr = new LinkedHashMap<>();

	/** dict */
	public static Map<String, Map<String, String>> dict = new LinkedHashMap<String, Map<String, String>>();

	/** unique */
	public static Map<String, String> unique = new LinkedHashMap<String, String>();

}
