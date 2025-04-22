package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_result
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "call result")
public interface CallResult {

	@Schema(description = "failure")
	String FAILURE = "0";

	@Schema(description = "success")
	String SUCCESS = "1";

}