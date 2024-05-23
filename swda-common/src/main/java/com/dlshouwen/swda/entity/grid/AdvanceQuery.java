package com.dlshouwen.swda.entity.grid;

import java.util.Date;
import java.util.List;

import com.dlshouwen.swda.entity.base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * advance query
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AdvanceQuery extends BaseEntity {

	private Long advanceQueryId;

	@NotBlank(message="查询方案名称不能为空")
	@Length(max=400, message="查询方案名称长度必须在0-400之间")
	private String advanceQueryName;

	@NotBlank(message="功能编号不能为空")
	@Length(max=400, message="功能编号长度必须在0-400之间")
	private String functionCode;

	@Length(max=200, message="备注长度必须在0-200之间")
	private String remark;

	private Long creator;

	private Date createTime;

	private Long editor;

	private Date editTime;
	
	private List<AdvanceQueryCondition> conditionList;
	
	private List<AdvanceQuerySort> sortList;

}