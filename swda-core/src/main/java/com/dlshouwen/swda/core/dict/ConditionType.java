package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "条件类型")
public class ConditionType {

	@Schema(description= "等于")
	public static Integer EQUALS = 0;

	@Schema(description= "不等于")
	public static Integer NOT_EQUALS = 1;

	@Schema(description= "匹配")
	public static Integer LIKE = 2;

	@Schema(description= "开始于")
	public static Integer START_WITH = 3;

	@Schema(description= "结束于")
	public static Integer END_WITH = 4;

	@Schema(description= "大于")
	public static Integer GREATER_THAN = 5;

	@Schema(description= "大于等于")
	public static Integer GREATER_THAN_EQUALS = 6;

	@Schema(description= "小于")
	public static Integer LESS_THAN = 7;

	@Schema(description= "小于等于")
	public static Integer LESS_THAN_EQUALS = 8;

	@Schema(description= "为空")
	public static Integer NULL = 9;

	@Schema(description= "不为空")
	public static Integer NOT_NULL = 10;

}