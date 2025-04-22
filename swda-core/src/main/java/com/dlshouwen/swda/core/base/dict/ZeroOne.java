package com.dlshouwen.swda.core.base.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict zero_one
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "zero one")
public interface ZeroOne {

	@Schema(description = "yes")
	String YES = "1";

	@Schema(description = "no")
	String NO = "0";

}