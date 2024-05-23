package com.dlshouwen.swda.entity.grid;

import com.dlshouwen.swda.entity.base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * advance query sort
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AdvanceQuerySort extends BaseEntity {

	private Long sortId;

	@NotNull(message="查询方案编号不能为空")
	private Long advanceQueryId;

	@NotBlank(message="字段不能为空")
	@Length(max=20, message="字段长度必须在0-20之间")
	private String sortField;

	@NotBlank(message="排序逻辑不能为空")
	@Length(max=2, message="排序逻辑长度必须在0-2之间")
	private String sortLogic = "1";

	@NotNull(message="排序码不能为空")
	private Integer sort = 0;

}