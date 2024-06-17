package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_logic
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "条件逻辑")
public class ConditionLogic {

	@Schema(description= "并且")
	public static Integer AND = 1;

	@Schema(description= "或者")
	public static Integer OR = 2;

}