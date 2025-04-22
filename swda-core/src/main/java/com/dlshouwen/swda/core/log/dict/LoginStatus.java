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
	String SUCCESS = "1";
	
	@Schema(description = "captcha error")
	String CAPTCHA_ERROR = "2";
	
	@Schema(description = "user not found")
	String USER_NOR_FOUND = "3";
	
	@Schema(description = "bad credentials")
	String BAD_CREDENTIALS = "4";
	
	@Schema(description = "credentials expired")
	String CREDENTIALS_EXPIRED = "5";
	
	@Schema(description = "user disabled")
	String USER_DISABLED = "6";

}