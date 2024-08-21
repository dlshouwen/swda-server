package com.dlshouwen.swda.bms.auth.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * auth vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "auth")
public class AuthVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "auth id")
	private Long authId;
	
	@Schema(description = "auth platform id")
	private Long authPlatformId;
	
	@Schema(description = "auth platform code")
	private String authPlatformCode;
	
	@Schema(description = "auth platform name")
	private String authPlatformName;

	@Schema(description = "auth platform type")
	private Integer authPlatformType;
	
	@Schema(description = "auth platform uuid")
	private String authPlatformUuid;

	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "user name")
	private String userName;

	@Schema(description = "tenant id")
	private Long tenantId;
	
	@Schema(description = "auth time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime authTime;

}