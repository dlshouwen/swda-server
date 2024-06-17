package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "条件类型")
public interface ConditionType {

	@Schema(description = "等于")
	int EQUALS = 0;

	@Schema(description = "不等于")
	int NOT_EQUALS = 1;

	@Schema(description = "匹配")
	int LIKE = 2;

	@Schema(description = "开始于")
	int START_WITH = 3;

	@Schema(description = "结束于")
	int END_WITH = 4;

	@Schema(description = "大于")
	int GREATER_THAN = 5;

	@Schema(description = "大于等于")
	int GREATER_THAN_EQUALS = 6;

	@Schema(description = "小于")
	int LESS_THAN = 7;

	@Schema(description = "小于等于")
	int LESS_THAN_EQUALS = 8;

	@Schema(description = "为空")
	int NULL = 9;

	@Schema(description = "不为空")
	int NOT_NULL = 10;

}