package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * dict vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "dict")
public class DictVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "dict category id")
	private String dictCategoryId;

	@Schema(description = "dict id")
	private String dictId;

	@Schema(description = "dict key")
	@NotNull(message = "字典键不能为空")
	private Long dictKey;

	@Schema(description = "dict value")
	@NotBlank(message = "字典值不能为空")
	private String dictValue;

	@Schema(description = "dict class")
	private String dictClass;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "update time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime updateTime;

}
