package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
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
 * @version 1.0.0
 */
@Data
@Schema(description = "dict")
public class DictVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "dict id")
	private Long dictId;
	
	@Schema(description = "dict name")
	private String dictName;

	@Schema(description = "dict type")
	private String dictType;

	@Schema(description = "dict label")
	@NotNull(message = "字典标签不能为空")
	private String dictLabel;

	@Schema(description = "dict value")
	@NotBlank(message = "字典值不能为空")
	private String dictValue;

	@Schema(description = "dict style")
	@NotBlank(message = "字典样式不能为空")
	private String dictStyle;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}
