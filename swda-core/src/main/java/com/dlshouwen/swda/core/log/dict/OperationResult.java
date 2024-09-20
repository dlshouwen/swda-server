package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_result
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "operation result")
public interface OperationResult {

	@Schema(description = "success")
	int SUCCESS = 1;

	@Schema(description = "token invalid")
	int TOKEN_INVALID = 2;

	@Schema(description = "no limit")
	int NO_LIMIT = 3;

	@Schema(description = "response error")
	int RESPONSE_ERROR = 4;

	@Schema(description = "force outline")
	int FORCE_OUTLINE = 5;

}