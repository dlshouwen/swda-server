package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sex
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="性别", description="性别")
public class Sex {

	@Schema(title="男")
	public static String MALE = "1";

	@Schema(title="女")
	public static String FEMALE = "2";

	@Schema(title="未知")
	public static String UNKNOWN = "9";

}