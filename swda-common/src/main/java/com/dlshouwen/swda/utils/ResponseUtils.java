package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.entity.base.R;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * response utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ResponseUtils {

	/**
	 * out
	 * @param response
	 * @param r
	 */
	public static void out(HttpServletResponse response, R r) {
//		set status
		response.setStatus(HttpStatus.OK.value());
//		set encoding
		response.setCharacterEncoding("UTF-8");
//		set content type
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		write data
		try {
			ObjectMapperUtils.getInstance().writeValue(response.getWriter(), r);
		} catch (IOException ignore) {
		}
	}
	
}