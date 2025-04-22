package com.dlshouwen.swda.core.unique.entity;

import java.util.List;

import lombok.Data;

/**
 * valid param
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class ValidParam {
	
	private String code;
	
	private String value;
	
	private List<Object> args;

}
