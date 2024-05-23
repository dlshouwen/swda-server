package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_result
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="响应结果", description="响应结果")
public class CallResult {

	@Schema(title="失败")
	public static String FAILURE = "0";

	@Schema(title="成功")
	public static String SUCCESS = "1";

}