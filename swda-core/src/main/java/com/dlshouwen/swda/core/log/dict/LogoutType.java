package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict logout_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "logout type")
public interface LogoutType {

	@Schema(description = "normal")
	String NORMAL = "1";

	@Schema(description = "token invalid")
	String TOKEN_INVALID = "2";

	@Schema(description = "force outline")
	String FORCE_OUTLINE = "3";

}