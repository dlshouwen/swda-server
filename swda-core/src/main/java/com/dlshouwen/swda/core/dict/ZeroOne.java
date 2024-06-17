package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict zero_one
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "zero one")
public interface ZeroOne {

	@Schema(description = "是")
	int YES = 1;

	@Schema(description = "否")
	int NO = 0;

}