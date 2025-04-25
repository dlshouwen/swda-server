package com.dlshouwen.swda.bms.platform.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * auth platform vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "auth platform")
public class AuthPlatformVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "auth platform id")
	private Long authPlatformId;

	@Schema(description = "open type")
	private String openType;

	@Schema(description = "client id")
	private String clientId;

	@Schema(description = "client secret")
	private String clientSecret;

	@Schema(description = "redirect uri")
	private String redirectUri;

	@Schema(description = "agent id")
	private String agentId;

	@Schema(description = "assist search")
	@Length(min = 0, max = 400, message = "辅助查询长度在 0-400 之间")
	private String assistSearch;
	
	@Schema(description = "sort")
	@Range(min = 0, message = "排序数据必须大于 0")
	private Integer sort;

	@Schema(description = "remark")
	@Length(min = 0, max = 200, message = "备注长度在 0-200 之间")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}