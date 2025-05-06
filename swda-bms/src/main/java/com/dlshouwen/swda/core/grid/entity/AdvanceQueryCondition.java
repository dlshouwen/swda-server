package com.dlshouwen.swda.core.grid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.grid.dict.ConditionType;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * advance query condition
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_advance_query_condition")
public class AdvanceQueryCondition extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -359741323067668889L;

	@TableId
	private Long conditionId;

	private Long advanceQueryId;

	private String leftParentheses;

	private String conditionField;

	private String conditionType = ConditionType.EQUALS;

	private String conditionValue;

	private String rightParentheses;

	private String conditionLogic;

	private Integer sort = 0;

}