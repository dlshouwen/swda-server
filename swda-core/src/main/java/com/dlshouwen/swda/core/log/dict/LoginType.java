package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * login type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "登录类型")
public interface LoginType {

	@Schema(description = "账户登录")
	int ACCOUNT = 1;
	
	@Schema(description = "短信登录")
	int MOBILE = 2;
	
	@Schema(description = "第三方平台授权登录")
	int THIRD = 3;

}