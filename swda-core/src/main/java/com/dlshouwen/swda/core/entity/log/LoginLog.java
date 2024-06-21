package com.dlshouwen.swda.core.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * login log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bms_login_log")
public class LoginLog extends BaseEntity {

	@TableId(value = "log_id", type = IdType.AUTO)
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

	private String loginStatus;

	private String isLogout;

	private String logoutType;

	private LocalDateTime logoutTime;

}