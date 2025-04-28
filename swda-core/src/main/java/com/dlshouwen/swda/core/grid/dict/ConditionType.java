package com.dlshouwen.swda.core.grid.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "condition type")
public interface ConditionType {

	@Schema(description = "equals")
	String EQUALS = "0";

	@Schema(description = "not equals")
	String NOT_EQUALS = "1";

	@Schema(description = "like")
	String LIKE = "2";
	
	@Schema(description = "right like")
	String RIGHT_LIKE = "3";
	
	@Schema(description = "left like")
	String LEFT_LIKE = "4";

	@Schema(description = "greater than")
	String GREATER_THAN = "5";

	@Schema(description = "greater than equals")
	String GREATER_THAN_EQUALS = "6";

	@Schema(description = "less than")
	String LESS_THAN = "7";

	@Schema(description = "less than equals")
	String LESS_THAN_EQUALS = "8";

	@Schema(description = "is null")
	String IS_NULL = "9";

	@Schema(description = "is not null")
	String IS_NOT_NULL = "10";

}