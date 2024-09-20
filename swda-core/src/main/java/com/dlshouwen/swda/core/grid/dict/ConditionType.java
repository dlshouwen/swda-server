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
	int EQUALS = 0;

	@Schema(description = "not equals")
	int NOT_EQUALS = 1;

	@Schema(description = "like")
	int LIKE = 2;
	
	@Schema(description = "right like")
	int RIGHT_LIKE = 3;
	
	@Schema(description = "left like")
	int LEFT_LIKE = 4;

	@Schema(description = "start with")
	int START_WITH = 5;

	@Schema(description = "end with")
	int END_WITH = 6;

	@Schema(description = "greater than")
	int GREATER_THAN = 7;

	@Schema(description = "greater than equals")
	int GREATER_THAN_EQUALS = 9;

	@Schema(description = "less than")
	int LESS_THAN = 9;

	@Schema(description = "less than equals")
	int LESS_THAN_EQUALS = 10;

	@Schema(description = "null")
	int NULL = 11;

	@Schema(description = "not null")
	int NOT_NULL = 12;

}