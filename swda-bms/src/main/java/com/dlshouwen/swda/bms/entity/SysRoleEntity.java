package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色编码
	 */
	private String roleCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 数据范围 {@link DataScopeEnum}
	 */
	private Integer dataScope;
	/**
	 * 机构ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long orgId;
	/**
	 * 租户ID
	 */
	private Long tenantId;
}
