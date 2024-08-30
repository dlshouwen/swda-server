package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict logout_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "登出类型")
public interface LogoutType {

	@Schema(description = "正常退出")
	int NORMAL = 1;

	@Schema(description = "TOKEN失效退出")
	int TOKEN_INVALID = 2;

	@Schema(description = "强制下线")
	int FORCE_OUTLINE = 3;

}