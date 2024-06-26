package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * attachment
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_attachment")
public class SysAttachmentEntity extends BaseEntity {

	private Long id;

	private String name;

	private String url;

	private Long size;

	private String platform;

	private Long tenantId;

}