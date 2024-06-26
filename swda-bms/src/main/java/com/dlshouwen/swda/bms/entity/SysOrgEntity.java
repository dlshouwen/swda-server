package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_org")
public class SysOrgEntity extends BaseEntity {
	/**
	 * 上级ID
	 */
	private Long id;
	private Long pid;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 负责人ID
	 */
	@TableField(updateStrategy = FieldStrategy.ALWAYS)
	private Long leaderId;
	/**
	 * 上级名称
	 */
	@TableField(exist = false)
	private String parentName;
	/**
	 * 租户ID
	 */
	private Long tenantId;
}