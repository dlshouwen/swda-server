package com.dlshouwen.swda.core.common.utils;

import com.dlshouwen.swda.core.common.exception.SwdaException;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * assert utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class AssertUtils {

	/**
	 * is blank
	 * @param str
	 * @param variable
	 */
	public static void isBlank(String str, String variable) {
		if (StrUtil.isBlank(str)) {
			throw new SwdaException(variable + "不能为空");
		}
	}

	/**
	 * is null
	 * @param object
	 * @param variable
	 */
	public static void isNull(Object object, String variable) {
		if (object == null) {
			throw new SwdaException(variable + "不能为空");
		}
	}

	/**
	 * is array empty
	 * @param array
	 * @param variable
	 */
	public static void isArrayEmpty(Object[] array, String variable) {
		if (ArrayUtil.isEmpty(array)) {
			throw new SwdaException(variable + "不能为空");
		}
	}

}