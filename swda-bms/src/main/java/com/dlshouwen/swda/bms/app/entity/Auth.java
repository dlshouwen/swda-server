package com.dlshouwen.swda.bms.app.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * auth
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@TableName("bms_auth")
public class Auth {

	@TableId
	private Long authId;
	
	private Integer openType;
	
	private String openId;
	
	private String username;
	
	private Long userId;
	
	private Long tenantId;
	
	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	private LocalDateTime authTime;
	
	private Integer status;

}