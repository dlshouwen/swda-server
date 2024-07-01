package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * third login
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@TableName("sys_third_login")
public class Auth {

	@TableId
	private Long id;

	private String openType;

	private String openId;

	private String username;

	private Long userId;

	private Long tenantId;

	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer deleted;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}