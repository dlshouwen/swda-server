package com.dlshouwen.swda.bms.log.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * login log vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "login log")
public class LoginLogVO implements Serializable, TransPojo {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "log id")
	private Long logId;

	@Schema(description = "user agent")
	private String userAgent;

	@Schema(description = "ip")
	private String ip;
	
	@Schema(description = "login type")
	private Integer loginType;

	@Schema(description = "login status")
	private Integer loginStatus;
	
	@Schema(description = "error reason")
	private String errorReason;

	@Schema(description = "login time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime loginTime;
	
	@Schema(description = "username")
	private String username;
	
	@Schema(description = "mobile")
	private String mobile;
	
	@Schema(description = "open type")
	private Integer openType;
	
	@Schema(description = "open id")
	private String openId;
	
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
	private Integer isLogout;

	@Schema(description = "logout type")
	private Integer logoutType;

	@Schema(description = "logout time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime logoutTime;

}
