package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * params vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "params")
public class AttrVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "param name")
	private String paramName;

	@Schema(description = "param type")
	private Integer paramType;

	@Schema(description = "param key")
	private String paramKey;

	@Schema(description = "param value")
	private String paramValue;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "version")
	private Integer version;

	@Schema(description = "deleted")
	private Integer deleted;

	@Schema(description = "creator")
	private Long creator;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "updater")
	private Long updater;

	@Schema(description = "update time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime updateTime;

}