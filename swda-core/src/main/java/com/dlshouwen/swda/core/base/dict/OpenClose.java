package com.dlshouwen.swda.core.base.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open_close
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "open close")
public interface OpenClose {

	@Schema(description = "open")
	String OPEN = "1";

	@Schema(description = "close")
	String CLOSE = "0";

}