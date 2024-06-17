package com.dlshouwen.swda.core.entity.base;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>数据对象</p>
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
public class Data {

    /** 当前系统版本号 */
    public static String version = "";

    /** 参数信息 */
    public static Map<String, String> attr = new HashMap<>();

    /** 字典信息 */
    public static Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();

    /** 唯一校验数据 */
    public static Map<String, String> unique = new HashMap<String, String>();

}
