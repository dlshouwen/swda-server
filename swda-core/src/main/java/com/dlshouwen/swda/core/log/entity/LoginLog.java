package com.dlshouwen.swda.core.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;
	
	private String userAgent;
	
	private Integer loginType;
	
	private Integer loginStatus;
	
	private String loginInfo;
	
	private String loginMessage;
	
	private Long tenantId;

	private Long userId;

	private String realName;

	private Long organId;

	private String organName;

	private LocalDateTime loginTime;

	private String ip;

	private Integer isLogout;

	private Integer logoutType;

	private LocalDateTime logoutTime;

}