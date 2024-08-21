package com.dlshouwen.swda.bms.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * auth
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@TableName("bms_auth")
public class Auth {

	@TableId
	private Long authId;
	
	private Long authPlatformId;

	private String authPlatformCode;
	
	private String authPlatformName;
	
	private Integer authPlatformType;
	
	private String authPlatformUuid;
	
	private Long userId;
	
	private String userName;

	private Long tenantId;
	
	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	private LocalDateTime authTime;
	
	private Integer status;

}