package com.dlshouwen.swda.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * third login config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@TableName("sys_third_login_config")
public class SysThirdLoginConfigEntity {

	@TableId
	private Long id;

	private String openType;

	private String clientId;

	private String clientSecret;

	private String redirectUri;

	private String agentId;

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