package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {
	
	private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别   0：男   1：女   2：未知
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 超级管理员   0：否   1：是
     */
    private Integer superAdmin;
    /**
     * 状态  {@link UserStatusEnum}
     */
    private Integer status;
    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * 租户ID
     */
    private Long tenantId;
}