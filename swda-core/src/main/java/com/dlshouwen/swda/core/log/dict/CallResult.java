package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_result
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "响应结果")
public interface CallResult {

	@Schema(description = "失败")
	int FAILURE = 0;

	@Schema(description = "成功")
	int SUCCESS = 1;

}