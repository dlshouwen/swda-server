package com.dlshouwen.swda.core.grid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.grid.dict.SortLogic;
import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * advance query sort
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bms_advance_query_sort")
public class AdvanceQuerySort extends BaseEntity {

	/** serial version uid */
	private static final long serialVersionUID = -1118733438495368189L;

	@TableId
	private Long sort_id;

	private Long advance_query_id;

	private String sort_field;

	private String sort_logic = SortLogic.ASC;

	private Integer sort = 0;

}