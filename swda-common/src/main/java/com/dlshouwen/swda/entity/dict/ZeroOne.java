package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict zero_one
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="是否", description="是否")
public class ZeroOne {

	@Schema(title="是")
	public static String YES = "1";

	@Schema(title="否")
	public static String NO = "0";

}