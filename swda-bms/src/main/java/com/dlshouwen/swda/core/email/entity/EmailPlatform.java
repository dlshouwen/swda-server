package com.dlshouwen.swda.common.email.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * email platform
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_email_platform")
public class EmailPlatform {

	@TableId
	private Long emailPlatformId;
	
	private String emailPlatformCode;

	private String emailPlatformName;

	private Integer emailPlatformType;
	
	private Integer status;

	private String groupName;

	private String mailHost;

	private Integer mailPort;

	private String mailFrom;

	private String mailPass;

	private String regionId;

	private String endpoint;

	private String accessKey;

	private String secretKey;
	
	private String assistSearch;
	
	private int sort;
	
	private String remark;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

}
