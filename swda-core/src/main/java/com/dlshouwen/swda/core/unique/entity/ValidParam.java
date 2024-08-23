package com.dlshouwen.swda.core.unique.entity;

import java.util.List;

import lombok.Data;

/**
 * valid param
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class ValidParam {
	
	private String code;
	
	private String key;
	
	private List<Object> attrs;

}
