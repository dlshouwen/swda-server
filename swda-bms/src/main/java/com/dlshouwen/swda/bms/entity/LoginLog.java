package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("sys_log_login")
public class LoginLog {

	@TableId
	private Long id;

	private String username;

	private String ip;

	private String address;

	private String userAgent;

	private Integer status;

	private Integer operation;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	private Long tenantId;

}