package com.dlshouwen.swda.core.xss.wrapper;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dlshouwen.swda.core.xss.utils.XssUtils;

/**
 * xss request wrapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * constructor
	 * @param request
	 */
	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * get parameter
	 * @param name
	 * @return filter xss value
	 */
	@Override
	public String getParameter(String name) {
		return filterXss(super.getParameter(name));
	}

	/**
	 * get parameter values
	 * @param name
	 * @return filter xss values
	 */
	@Override
	public String[] getParameterValues(String name) {
//		get parameters
		String[] parameters = super.getParameterValues(name);
//		if empty return null
		if (parameters == null || parameters.length == 0) {
			return null;
		}
//		for each parameter
		for (int i = 0; i < parameters.length; i++) {
//			filter xss
			parameters[i] = filterXss(parameters[i]);
		}
//		return parameters
		return parameters;
	}

	/**
	 * get parameter map
	 * @return filter xss parameter map
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
//		defined params
		Map<String, String[]> params = new LinkedHashMap<>();
//		get parameter map
		Map<String, String[]> parameters = super.getParameterMap();
//		for each parameters
		for (String key : parameters.keySet()) {
//			get values
			String[] values = parameters.get(key);
//			for each value
			for (int i = 0; i < values.length; i++) {
//				filter xss
				values[i] = filterXss(values[i]);
			}
//			put params
			params.put(key, values);
		}
//		return params
		return params;
	}

	/**
	 * set header
	 * @param name
	 * @return filter xss header
	 */
	@Override
	public String getHeader(String name) {
		return filterXss(super.getHeader(name));
	}

	/**
	 * filter xss
	 * @param content
	 * @return filter xss content
	 */
	private String filterXss(String content) {
//		if empty return content
		if (StrUtil.isBlank(content)) {
			return content;
		}
//		return filter xss content
		return XssUtils.filter(content);
	}

}