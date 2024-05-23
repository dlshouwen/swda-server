package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict condition_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="条件类型", description="条件类型")
public class ConditionType {

	@Schema(title="等于")
	public static String EQUALS = "0";

	@Schema(title="不等于")
	public static String NOT_EQUALS = "1";

	@Schema(title="匹配")
	public static String LIKE = "2";

	@Schema(title="开始于")
	public static String START_WITH = "3";

	@Schema(title="结束于")
	public static String END_WITH = "4";

	@Schema(title="大于")
	public static String GREATER_THAN = "5";

	@Schema(title="大于等于")
	public static String GREATER_THAN_EQUALS = "6";

	@Schema(title="小于")
	public static String LESS_THAN = "7";

	@Schema(title="小于等于")
	public static String LESS_THAN_EQUALS = "8";

	@Schema(title="为空")
	public static String NULL = "9";

	@Schema(title="不为空")
	public static String NOT_NULL = "10";

}