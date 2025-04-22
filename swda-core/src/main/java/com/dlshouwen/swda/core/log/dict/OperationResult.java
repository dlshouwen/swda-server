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
	String SUCCESS = "1";

	@Schema(description = "token invalid")
	String TOKEN_INVALID = "2";

	@Schema(description = "no limit")
	String NO_LIMIT = "3";

	@Schema(description = "response error")
	String RESPONSE_ERROR = "4";

	@Schema(description = "force outline")
	String FORCE_OUTLINE = "5";

}