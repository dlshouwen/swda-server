package com.dlshouwen.swda.bms.core.grid.vo;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fhs.core.trans.vo.TransPojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * advance query vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "advance query")
public class AdvanceQueryVO implements Serializable, TransPojo {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "advance query id")
	private Long advanceQueryId;

	@Schema(description = "advance query name")
	@NotBlank(message="查询方案名称不能为空")
	@Length(min=0, max=400, message="查询方案名称长度必须在0-400之间")
	private String advanceQueryName;

	@Schema(description = "function code")
	@NotBlank(message="功能编号不能为空")
	@Length(min=0, max=400, message="功能编号长度必须在0-400之间")
	private String functionCode;

	@Schema(description = "remark")
	@Length(min=0, max=200, message="备注长度必须在0-200之间")
	private String remark;
	
	@Valid
	private List<AdvanceQueryConditionVO> conditionList;
	
	@Valid
	private List<AdvanceQuerySortVO> sortList;

}