package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_logic
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "条件逻辑")
public interface ConditionLogic {

	@Schema(description = "并且")
	int AND = 1;

	@Schema(description = "或者")
	int OR = 2;

}