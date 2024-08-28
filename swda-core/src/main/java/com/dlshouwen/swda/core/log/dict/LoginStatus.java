package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "登录状态")
public interface LoginStatus {

	@Schema(description = "登录成功")
	int SUCCESS = 1;
	
	@Schema(description = "用户不存在")
	int USER_NOT_EXIST = 2;
	
	@Schema(description = "验证码错误")
	int CAPTCHA_ERROR = 3;

	@Schema(description = "密码错误")
	int PASSWORD_ERROR = 3;

	@Schema(description = "用户无效")
	int USER_INVALID = 4;

	@Schema(description = "用户无权限")
	int NO_LIMIT = 5;

}