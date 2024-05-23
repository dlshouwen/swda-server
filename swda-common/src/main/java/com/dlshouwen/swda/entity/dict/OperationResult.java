package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_result
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="操作结果", description="操作结果")
public class OperationResult {

	@Schema(title="访问成功")
	public static String SUCCESS = "1";

	@Schema(title="TOKEN超时")
	public static String TOKEN_INVALID = "2";

	@Schema(title="无权访问")
	public static String NO_LIMIT = "3";

	@Schema(title="响应错误")
	public static String RESPONSE_ERROR = "4";

	@Schema(title="强制下线")
	public static String FORCE_OUTLINE = "5";

}