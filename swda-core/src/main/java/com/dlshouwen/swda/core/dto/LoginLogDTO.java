package com.dlshouwen.swda.core.dto;

import java.time.LocalDateTime;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * login log dto
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class LoginLogDTO {

	private Long logId;

	private String userAgent;
	
	private String token;

	private Long tenantId;
	
	private Long userId;
	
	private String userName;
	
	private Long organId;
	
	private String organName;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime loginTime;

	private String ip;

	private Integer loginStatus;

	private Integer isLogout;

	private Integer logoutType;

	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime logoutTime;

}
