package com.dlshouwen.swda.core.grid.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_logic
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "condition logic")
public interface ConditionLogic {

	@Schema(description = "and")
	String AND = "1";

	@Schema(description = "or")
	String OR = "2";

}