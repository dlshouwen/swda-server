package com.dlshouwen.swda.entity.base;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cascader item
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class CascaderItem extends BaseEntity {
	
	private String id;
	
	private String pid;
	
	private String value;

	private String label;
	
	private List<CascaderItem> children;
	
}
