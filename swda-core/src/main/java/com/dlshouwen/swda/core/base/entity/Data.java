package com.dlshouwen.swda.core.base.entity;

import java.util.HashMap;
import java.util.Map;

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

}
