package com.dlshouwen.swda.core.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.base.enums.ResultCode;
import com.dlshouwen.swda.core.base.utils.HttpContextUtils;
import com.dlshouwen.swda.core.base.utils.JsonUtils;

import java.io.IOException;

/**
 * security authentication entry point
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * commence
	 * @param request
	 * @param response
	 * @param authException
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
//		set response
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
//		writer
		response.getWriter().print(JsonUtils.toJsonString(R.error(ResultCode.UNAUTHORIZED)));
	}

}