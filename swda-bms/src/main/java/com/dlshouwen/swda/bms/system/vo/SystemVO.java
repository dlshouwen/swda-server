package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * system vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "system")
public class SystemVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "system id")
	private Long systemId;
	
	@Schema(description = "system code")
	@NotBlank(message = "系统编码不能为空")
	private String systemCode;

	@Schema(description = "system name")
	@NotBlank(message = "系统名称不能为空")
	private String systemName;
	
	@Schema(description = "icon")
	private String icon;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}