package com.dlshouwen.swda.bms.permission.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * post vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "post")
public class PostVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "post id")
	private Long postId;

	@Schema(description = "post code")
	@NotBlank(message = "岗位编码不能为空")
	private String postCode;

	@Schema(description = "post name")
	@NotBlank(message = "岗位名称不能为空")
	private String postName;

	@Schema(description = "status")
	@Range(min = 0, max = 1, message = "状态不正确")
	private String status;

	@Schema(description = "assist search")
	private String assistSearch;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}