package com.dlshouwen.swda.core.log.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * login log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_login_log")
public class LoginLog extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -9026337418739933032L;

	@TableId
	private Long loginLogId;
	
	private String userAgent;
	
	private String loginType;
	
	private String loginStatus;
	
	private String loginInfo;
	
	private String loginMessage;
	
	private Long tenantId;

	private Long userId;

	private String realName;

	private Long organId;

	private String organName;

	private LocalDateTime loginTime;

	private String ip;

	private String isLogout;

	private String logoutType;

	private LocalDateTime logoutTime;

}