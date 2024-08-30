package com.dlshouwen.swda.core.xss.filter;

import cn.hutool.core.util.StrUtil;

import com.dlshouwen.swda.core.common.utils.HttpContextUtils;
import com.dlshouwen.swda.core.xss.properties.XssProperties;
import com.dlshouwen.swda.core.xss.utils.XssUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.util.PathMatcher;

import java.io.IOException;

/**
 * xss filter json deserializer
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@AllArgsConstructor
public class XssFilterJsonDeserializer extends JsonDeserializer<String> {
	
	/** properties */
	private final XssProperties properties;
	
	/** path matcher */
	private final PathMatcher pathMatcher;

	/**
	 * deserialize
	 * @param jsonParser
	 * @param deserializationContext
	 * @throws IOException
	 * @return 
	 */
	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//		get value
		String value = jsonParser.getValueAsString();
//		if blank then return null
		if (StrUtil.isBlank(value)) {
			return null;
		}
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		if null then return value
		if (request == null) {
			return value;
		}
//		if exclude urls then pass
		boolean match = properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
		if (match) {
			return value;
		}
//		filter
		return XssUtils.filter(value);
	}

	/**
	 * handled type
	 * @return class
	 */
	@Override
	public Class<String> handledType() {
		return String.class;
	}

}
