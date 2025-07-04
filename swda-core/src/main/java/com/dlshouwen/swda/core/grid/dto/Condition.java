package com.dlshouwen.swda.core.grid.dto;

import lombok.Data;

/**
 * condition
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class Condition {

	/** left parentheses */
	private String leftParentheses;

	/** condition field */
	private String conditionField;

	/** condition type */
	private String conditionType;

	/** condition value */
	private String conditionValue;

	/** right parentheses */
	private String rightParentheses;

	/** condition logic */
	private String conditionLogic;

}
