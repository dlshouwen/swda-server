package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sex
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "性别")
public class Sex {

	@Schema(description= "男")
	public static Integer MALE = 1;

	@Schema(description= "女")
	public static Integer FEMALE = 2;

	@Schema(description= "未知")
	public static Integer UNKNOWN = 9;

}