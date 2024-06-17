package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict zero_one
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(description="zero one")
public class ZeroOne {

	@Schema(description="是")
	public static Integer YES = 1;

	@Schema(description="否")
	public static Integer NO = 0;

}