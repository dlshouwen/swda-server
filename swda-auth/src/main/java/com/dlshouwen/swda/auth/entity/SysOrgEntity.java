package com.dlshouwen.swda.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * organ
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_org")
public class SysOrgEntity extends BaseEntity {

	private Long organId;

	private Long pid;

	private String name;

	private Integer sort;

	@TableField(updateStrategy = FieldStrategy.ALWAYS)
	private Long leaderId;

	@TableField(exist = false)
	private String parentName;

	private Long tenantId;

}