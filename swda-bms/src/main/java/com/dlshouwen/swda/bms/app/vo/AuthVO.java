package com.dlshouwen.swda.bms.app.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * auth vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "auth")
public class AuthVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "auth id")
	private Long authId;
	
	@Schema(description = "open type")
	private Integer openType;
	
	@Schema(description = "open id")
	private String openId;

	@Schema(description = "user name")
	private String username;
	
	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "tenant id")
	private Long tenantId;
	
	@Schema(description = "auth time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime authTime;

}