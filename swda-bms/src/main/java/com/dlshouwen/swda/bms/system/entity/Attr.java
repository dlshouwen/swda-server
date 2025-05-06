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
 * attr
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_attr")
public class Attr extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -8581501890537686165L;

	@TableId
	private String attrId;

	private String attrName;

	private String attrType;
	
	private String dataType;

	private String dataFormat;

	private String options;

	private String valid;
	
	private String content;
	
	private String isSpecial;
	
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