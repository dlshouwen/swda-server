package com.dlshouwen.swda.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * result code
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

	SUCCESS(200, "访问成功"), 
	
	UNAUTHORIZED(401, "未授权"), 
	
	FORBIDDEN(403, "无权访问"), 
	
	REFRESH_TOKEN_INVALID(400, "TOKEN失效"),

	INTERNAL_SERVER_ERROR(500, "服务器异常");

	/** code */
	private final int code;

	/** message */
	private final String message;

}
