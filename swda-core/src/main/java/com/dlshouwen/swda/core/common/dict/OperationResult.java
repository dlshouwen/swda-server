package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_result
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "操作结果")
public interface OperationResult {

	@Schema(description = "访问成功")
	int SUCCESS = 1;

	@Schema(description = "TOKEN超时")
	int TOKEN_INVALID = 2;

	@Schema(description = "无权访问")
	int NO_LIMIT = 3;

	@Schema(description = "响应错误")
	int RESPONSE_ERROR = 4;

	@Schema(description = "强制下线")
	int FORCE_OUTLINE = 5;

}