package com.dlshouwen.swda.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {

	private String username;

	private String password;

	private String realName;

	private String avatar;

	private Integer gender;

	private String email;

	private String mobile;

	private Long orgId;

	private Integer superAdmin;

	private Integer status;

	@TableField(exist = false)
	private String orgName;

	private Long tenantId;

}