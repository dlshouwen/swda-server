package com.dlshouwen.swda.core.grid.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dlshouwen.swda.core.grid.dict.SortLogic;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * advance query sort
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "advance query sort")
public class AdvanceQuerySortVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "sort id")
	private Long sortId;

	@Schema(description = "advance query id")
	@NotNull(message="查询方案编号不能为空")
	private Long advanceQueryId;

	@Schema(description = "sort field")
	@NotBlank(message="字段不能为空")
	@Length(min=0, max=20, message="字段长度必须在0-20之间")
	private String sortField;

	@Schema(description = "sort logic")
	@NotNull(message="排序逻辑不能为空")
	private String sortLogic = SortLogic.ASC;

	@Schema(description = "sort")
	@NotNull(message="排序码不能为空")
	private Integer sort = 0;

}