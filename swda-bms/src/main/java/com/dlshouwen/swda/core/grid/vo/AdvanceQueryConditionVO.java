package com.dlshouwen.swda.core.grid.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dlshouwen.swda.core.grid.dict.ConditionType;
import com.fhs.core.trans.vo.TransPojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * advance query condition vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "advance query condition")
public class AdvanceQueryConditionVO implements Serializable, TransPojo {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "condition id")
	private Long conditionId;

	@Schema(description = "advance query id")
	@NotNull(message="查询方案编号不能为空")
	private Long advanceQueryId;

	@Schema(description = "left parentheses")
	@Length(min=0, max=10, message="左括号长度必须在0-10之间")
	private String leftParentheses;

	@Schema(description = "condition field")
	@NotBlank(message="字段不能为空")
	@Length(min=0, max=20, message="字段长度必须在0-20之间")
	private String conditionField;

	@Schema(description = "condition type")
	@NotNull(message="条件不能为空")
	private String conditionType = ConditionType.EQUALS;

	@Schema(description = "condition value")
	@Length(min=0, max=400, message="值长度必须在0-400之间")
	private String conditionValue;

	@Schema(description = "right parentheses")
	@Length(min=0, max=10, message="右括号长度必须在0-10之间")
	private String rightParentheses;

	@Schema(description = "condition logic")
	private String conditionLogic;

	@Schema(description = "sort")
	@NotNull(message="排序码不能为空")
	private Integer sort = 0;

}