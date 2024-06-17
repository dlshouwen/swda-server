package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_result
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "响应结果")
public class CallResult {

	@Schema(description= "失败")
	public static Integer FAILURE = 0;

	@Schema(description= "成功")
	public static Integer SUCCESS = 1;

}