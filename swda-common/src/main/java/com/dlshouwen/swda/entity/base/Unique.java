package com.dlshouwen.swda.entity.base;

import java.util.List;

import lombok.Data;

/**
 * unique
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class Unique {
	
	private String code;
	
	private String value;
	
	private List<Object> args;

}
