package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open_close
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "open close")
public interface OpenClose {

	@Schema(description = "open")
	int OPEN = 1;

	@Schema(description = "close")
	int CLOSE = 0;

}