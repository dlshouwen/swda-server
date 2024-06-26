package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * role
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {

	private Long id;

	private String name;

	private String roleCode;

	private String remark;

	private Integer dataScope;

	@TableField(fill = FieldFill.INSERT)
	private Long orgId;

	private Long tenantId;

}