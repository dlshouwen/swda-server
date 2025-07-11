package com.dlshouwen.swda.bms.system.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * menu
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_menu")
public class Menu extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -1233326849196066954L;

	@TableId
	private Long menuId;

	private Long preMenuId;
	
	private Long systemId;

	private String menuCode;
	
	private String menuName;
	
	private String menuType;
	
	private String authority;
	
	private String path;
	
	private String component;
	
	private String icon;
	
	private String assistSearch;
	
	private Integer sort;
	
	private String remark;
	
	private Long tenantId;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
	
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleted;

}