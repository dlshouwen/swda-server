package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "登录状态")
public interface LoginStatus {

	@Schema(description = "登录成功")
	int SUCCESS = 1;
	
	@Schema(description = "验证码错误")
	int CAPTCHA_ERROR = 2;
	
	@Schema(description = "用户不存在")
	int USER_NOR_FOUND = 3;
	
	@Schema(description = "认证错误")
	int BAD_CREDENTIALS = 4;
	
	@Schema(description = "认证已过期")
	int CREDENTIALS_EXPIRED = 5;
	
	@Schema(description = "用户已禁用")
	int USER_DISABLED = 6;

}