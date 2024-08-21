package com.dlshouwen.swda.bms.sms.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sms platform
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_platform")
@Schema(description = "sms platform")
public class SmsPlatform extends BaseEntity {

	@TableId
	@Schema(description = "sms platform id")
	private Long smsPlatformId;
	
	@Schema(description = "sms platform code")
	private Long smsPlatformCode;
	
	@Schema(description = "sms platform name")
	private String smsPlatformName;

	@Schema(description = "platform type")
	private Integer smsPlatformType;

	@Schema(description = "status")
	private Integer status;

	@Schema(description = "sign name")
	private String signName;

	@Schema(description = "template id")
	private String templateId;

	@Schema(description = "app id")
	private String appId;

	@Schema(description = "sender id")
	private String senderId;

	@Schema(description = "url")
	private String url;

	@Schema(description = "access key")
	private String accessKey;

	@Schema(description = "secret key")
	private String secretKey;
	
	@Schema(description = "assist search")
	private String assistSearch;
	
	@Schema(description = "sort")
	private int sort;
	
	@Schema(description = "remark")
	private String remark;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "creator")
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "create time")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "updater")
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "update time")
    private LocalDateTime updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "version")
    private Integer version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "deleted")
    private Integer deleted;

}