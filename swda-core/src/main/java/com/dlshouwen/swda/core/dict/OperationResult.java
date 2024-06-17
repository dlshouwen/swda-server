package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_result
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "操作结果")
public class OperationResult {

	@Schema(description= "访问成功")
	public static Integer SUCCESS = 1;

	@Schema(description= "TOKEN超时")
	public static Integer TOKEN_INVALID = 2;

	@Schema(description= "无权访问")
	public static Integer NO_LIMIT = 3;

	@Schema(description= "响应错误")
	public static Integer RESPONSE_ERROR = 4;

	@Schema(description= "强制下线")
	public static Integer FORCE_OUTLINE = 5;

}