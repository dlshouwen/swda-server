package com.dlshouwen.swda.core.grid.entity;

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
 * advance query
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_advance_query")
public class AdvanceQuery extends BaseEntity {

	@TableId
	private Long advanceQueryId;

	private String advanceQueryName;

	private String functionCode;

	private String remark;

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
    private Integer deleted;

}