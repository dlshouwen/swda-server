package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "login status")
public interface LoginStatus {

	@Schema(description = "success")
	int SUCCESS = 1;
	
	@Schema(description = "captcha error")
	int CAPTCHA_ERROR = 2;
	
	@Schema(description = "user not found")
	int USER_NOR_FOUND = 3;
	
	@Schema(description = "bad credentials")
	int BAD_CREDENTIALS = 4;
	
	@Schema(description = "credentials expired")
	int CREDENTIALS_EXPIRED = 5;
	
	@Schema(description = "user disabled")
	int USER_DISABLED = 6;

}