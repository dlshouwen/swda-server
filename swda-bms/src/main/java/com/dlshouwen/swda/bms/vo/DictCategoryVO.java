package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * dict type vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "dict type")
public class DictCategoryVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "dict category id")
	private String dictCategoryId;
	
	@Schema(description = "dict category name")
	private String dictCategoryName;

	@Schema(description = "source type")
	private String sourceType;
	
	@Schema(description = "source sql")
	private String sourceSql;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "tenant id")
	private Long tenantId;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "update time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime updateTime;

}