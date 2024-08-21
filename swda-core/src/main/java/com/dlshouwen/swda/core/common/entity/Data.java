package com.dlshouwen.swda.core.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * data
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class Data {

	/** version */
	public static String version = "";

	/** attr */
	public static Map<String, String> attr = new HashMap<>();

	/** dict */
	public static Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();

	/** unique */
	public static Map<String, String> unique = new HashMap<String, String>();

}
