package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * params vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "attr")
public class AttrVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "attr id")
	private String attrId;

	@Schema(description = "attr name")
	private String attrName;

	@Schema(description = "attr type")
	private String attrType;

	@Schema(description = "data type")
	private String dataType;

	@Schema(description = "data format")
	private String dataFormat;
	
	@Schema(description = "options")
	private String options;
	
	@Schema(description = "valid")
	private String valid;
	
	@Schema(description = "content")
	private String content;
	
	@Schema(description = "is special")
	private String isSpecial;
	
	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "remark")
	private String remark;
	
	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}