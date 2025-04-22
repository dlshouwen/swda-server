package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * login type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "login type")
public interface LoginType {

	@Schema(description = "account")
	String ACCOUNT = "1";
	
	@Schema(description = "mobile")
	String MOBILE = "2";
	
	@Schema(description = "third")
	String THIRD = "3";

}