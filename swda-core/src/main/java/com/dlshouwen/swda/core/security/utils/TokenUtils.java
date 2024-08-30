package com.dlshouwen.swda.core.security.utils;

import com.dlshouwen.swda.core.common.utils.HttpContextUtils;

import cn.hutool.core.lang.UUID;
import jakarta.servlet.http.HttpServletRequest;

/**
 * token utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class TokenUtils {

	/**
	 * generator
	 * @return uuid
	 */
	public static String generator() {
		return UUID.fastUUID().toString(true);
	}

	/**
	 * get access token
	 * @return access token
	 */
	public static String getAccessToken() {
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		request is empty then return null
		if (request == null) {
			return null;
		}
//		get access token
		return getAccessToken(request);
	}

	/**
	 * get access token
	 * @param request
	 * @return access token
	 */
	public static String getAccessToken(HttpServletRequest request) {
//		get and return access token
		return request.getHeader("access_token");
	}

}
