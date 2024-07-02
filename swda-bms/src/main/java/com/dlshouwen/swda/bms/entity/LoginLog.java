package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * login log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@TableName("bms_login_log")
public class LoginLog {

	@TableId
	private Long logId;

	private String userAgent;
	
	private String token;
	
	private Long tenantId;
	
	private Long userId;
	
	private String userName;
	
	private Long organId;
	
	private String organName;
	
	private LocalDateTime loginTime;
	
	private String ip;
	
	private Integer loginStatus;
	
	private Integer isLogout;
	
	private Integer logoutType;
	
	private LocalDateTime logoutTime;
	
}