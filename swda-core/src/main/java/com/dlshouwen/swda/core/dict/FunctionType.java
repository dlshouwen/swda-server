package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict function_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "功能类型")
public class FunctionType {

	@Schema(description= "菜单")
	public static Integer MENU = 1;

	@Schema(description= "按钮")
	public static Integer BUTTON = 2;

}