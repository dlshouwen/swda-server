package com.dlshouwen.swda.bms.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * auth platform
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@TableName("bms_auth_platform")
public class AuthPlatform {

	@TableId
	private Long authPlatformId;

	private Integer openType;
	
	private String clientId;
	
	private String clientSecret;

	private String redirectUri;

	private String agentId;

	private String assistSearch;
	
	private Integer sort;
	
	private String remark;
	
	private Long tenantId;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
	
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

}