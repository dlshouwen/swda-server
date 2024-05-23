package com.dlshouwen.swda.entity.grid;

import com.dlshouwen.swda.entity.base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * advance query condition
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AdvanceQueryCondition extends BaseEntity {

	private Long conditionId;

	@NotNull(message="查询方案编号不能为空")
	private Long advanceQueryId;

	@Length(max=10, message="左括号长度必须在0-10之间")
	private String leftParentheses;

	@Length(max=20, message="字段长度必须在0-20之间")
	private String conditionField;

	@NotBlank(message="条件不能为空")
	@Length(max=4, message="条件长度必须在0-4之间")
	private String conditionType = "0";

	@Length(max=400, message="值长度必须在0-400之间")
	private String conditionValue;

	@Length(max=10, message="右括号长度必须在0-10之间")
	private String rightParentheses;

	@Length(max=2, message="逻辑长度必须在0-2之间")
	private String conditionLogic;

	@NotNull(message="排序码不能为空")
	private Integer sort = 0;

}