package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_logic
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="条件逻辑", description="条件逻辑")
public class ConditionLogic {

	@Schema(title="并且")
	public static String AND = "1";

	@Schema(title="或者")
	public static String OR = "2";

}