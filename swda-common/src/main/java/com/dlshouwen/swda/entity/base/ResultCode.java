package com.dlshouwen.swda.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
	
	SUCCESS(0, "访问成功"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权访问"),
    REFRESH_TOKEN_INVALID(400, "TOKEN失效"),
    INTERNAL_SERVER_ERROR(500, "服务器异常");

    private final int code;
    private final String message;

}
