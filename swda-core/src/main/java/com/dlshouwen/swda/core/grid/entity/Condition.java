package com.dlshouwen.swda.core.grid.entity;

import lombok.Data;

/**
 * condition
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class Condition {

	/** left parentheses */
	private String leftParentheses;

	/** condition field */
	private String conditionField;

	/** condition type */
	private Integer conditionType;

	/** condition value */
	private String conditionValue;

	/** right parentheses */
	private String rightParentheses;

	/** condition logic */
	private Integer conditionLogic;

}
