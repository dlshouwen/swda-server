package com.dlshouwen.swda.bms.system.entity;

import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * region
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_region")
public class Region extends BaseEntity {

	@TableId(type=IdType.INPUT)
	private Integer regionId;

	private Integer preRegionId;

	private String regionType;

	private String regionName;

	private String regionFullName;

	private String regionNamePinyin;

	private String regionNameFullPinyin;

	private String postalCode;

	private String isOpen;

	private String isHot;

	private Integer sort;

	private String remark;

	private Long tenantId;

	@Version
    @TableField(fill = FieldFill.INSERT)
	private Integer version;

	@TableField(fill=FieldFill.INSERT)
	private Long creator;

	@TableField(fill=FieldFill.INSERT)
	private LocalDateTime createTime;
	
	@TableField(fill=FieldFill.INSERT_UPDATE)
	private Long updater;

	@TableField(fill=FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableField(fill=FieldFill.INSERT)
	private String deleted;

	@TableField(exist=false)
	private Boolean hasChildren;

}
