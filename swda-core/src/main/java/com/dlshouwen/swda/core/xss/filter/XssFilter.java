package com.dlshouwen.swda.core.xss.filter;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dlshouwen.swda.core.xss.properties.XssProperties;
import com.dlshouwen.swda.core.xss.wrapper.XssRequestWrapper;

import java.io.IOException;

/**
 * xss filter
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@AllArgsConstructor
public class XssFilter extends OncePerRequestFilter {
	
	/** properties */
	private final XssProperties properties;
	
	/** path matcher */
	private final PathMatcher pathMatcher;

	/**
	 * do filter internal
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException, ServletException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(new XssRequestWrapper(request), response);
	}

	/**
	 * should not filter
	 * @param request
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
//		get content type
		String contentType = request.getContentType();
//		if content type is blank or json then return
		if (StrUtil.isBlank(contentType) || StrUtil.startWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
			return true;
		}
//		pass exclude urils
		return properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
	}

}
