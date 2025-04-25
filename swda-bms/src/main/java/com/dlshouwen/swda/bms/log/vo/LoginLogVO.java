package com.dlshouwen.swda.bms.log.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * login log vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "login log")
public class LoginLogVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "login log id")
	private Long loginLogId;

	@Schema(description = "user agent")
	private String userAgent;

	@Schema(description = "ip")
	private String ip;
	
	@Schema(description = "login type")
	private String loginType;

	@Schema(description = "login status")
	private String loginStatus;
	
	@Schema(description = "login time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime loginTime;
	
	@Schema(description = "login info")
	private String loginInfo;
	
	@Schema(description = "login message")
	private String loginMessage;
	
	@Schema(description = "tenant id")
	private Long tenantId;
	
	@Schema(description = "user id")
	private Long userId;
	
	@Schema(description = "real name")
	private String realName;
	
	@Schema(description = "organ id")
	private Long organId;
	
	@Schema(description = "organ name")
	private String organName;

	@Schema(description = "is logout")
	private String isLogout;

	@Schema(description = "logout type")
	private String logoutType;

	@Schema(description = "logout time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime logoutTime;

}
