package com.dlshouwen.swda.auth.vo;

import com.dlshouwen.swda.core.entity.base.TreeNode;
import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * organ vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "organ")
public class SysOrgVO extends TreeNode<SysOrgVO> {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "机构名称")
	@NotBlank(message = "机构名称不能为空")
	private String name;

	@Schema(description = "排序")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "负责人ID")
	private Long leaderId;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "上级名称")
	private String parentName;

}