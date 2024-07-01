package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * menu
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Permission extends BaseEntity {

	private Long id;

	private Long pid;

	private String name;

	private String url;

	private String authority;

	private Integer type;

	private Integer openStyle;

	@TableField(updateStrategy = FieldStrategy.ALWAYS)
	private String icon;

	private Integer sort;

}