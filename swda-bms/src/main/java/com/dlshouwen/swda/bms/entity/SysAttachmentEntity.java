package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_attachment")
public class SysAttachmentEntity extends BaseEntity {

	/**
	 * 附件大小
	 */
	private Long id;

	/**
	 * 附件名称
	 */
	private String name;

	/**
	 * 附件地址
	 */
	private String url;

	/**
	 * 附件大小
	 */
	private Long size;

	/**
	 * 存储平台
	 */
	private String platform;
	/**
	 * 租户ID
	 */
	private Long tenantId;

}