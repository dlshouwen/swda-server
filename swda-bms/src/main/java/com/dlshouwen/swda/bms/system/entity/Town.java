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
 * town
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_town")
public class Town extends BaseEntity {

	@TableId(type=IdType.INPUT)
	private Integer townId;

	private Integer provinceId;

	private Integer cityId;

	private Integer countyId;

	private String townName;

	private String townFullName;

	private String townNamePinyin;

	private String townNameFullPinyin;

	private String postalCode;

	private Integer sort;

	private String remark;

	private Long tenantId;

	@Version
	private Integer version;

	@TableField(fill=FieldFill.INSERT)
	private Long creator;

	@TableField(fill=FieldFill.INSERT)
	private LocalDateTime createTime;

	private Long updater;

	private LocalDateTime updateTime;

	private String deleted;

	private Long deleter;

	private LocalDateTime deleteTime;

}
