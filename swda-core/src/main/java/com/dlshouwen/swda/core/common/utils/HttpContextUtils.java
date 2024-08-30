package com.dlshouwen.swda.core.common.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http context utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class HttpContextUtils {

	/**
	 * get http servlet request
	 * @return request
	 */
	public static HttpServletRequest getHttpServletRequest() {
//		get request attributes
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		if empty return null
		if (requestAttributes == null) {
			return null;
		}
//		get request and return
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * get http servlet response
	 * @return response
	 */
	public static HttpServletResponse getHttpServletResponse() {
//		get request attributes
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//		if empty return null
		if (requestAttributes == null) {
			return null;
		}
//		get response and return
		return ((ServletRequestAttributes) requestAttributes).getResponse();
	}

	/**
	 * get parameter map
	 * @param request
	 * @return parameter map
	 */
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
//		get parameter names
		Enumeration<String> parameters = request.getParameterNames();
//		defined params
		Map<String, String> params = new HashMap<>();
//		for each parameters
		while (parameters.hasMoreElements()) {
//			get parameter
			String parameter = parameters.nextElement();
//			get value
			String value = request.getParameter(parameter);
//			put to params
			if (StrUtil.isNotBlank(value)) {
				params.put(parameter, value);
			}
		}
//		return params
		return params;
	}

	/**
	 * get domain
	 * @return domain
	 */
	public static String getDomain() {
//		get request
		HttpServletRequest request = getHttpServletRequest();
//		get domain
		return getDomain(request);
	}

	/**
	 * get domain
	 * @param request
	 * @return domain
	 */
	public static String getDomain(HttpServletRequest request) {
//		get domain
		String domain = request.getHeader(HttpHeaders.ORIGIN);
//		if empty
		if (StrUtil.isBlank(domain)) {
//			get domain from referer
			domain = request.getHeader(HttpHeaders.REFERER);
		}
//		remove domain
		return StringUtils.removeEnd(domain, "/");
	}

	/**
	 * get origin
	 * @return origin
	 */
	public static String getOrigin() {
//		get request
		HttpServletRequest request = getHttpServletRequest();
//		get origin
		return request.getHeader(HttpHeaders.ORIGIN);
	}

}