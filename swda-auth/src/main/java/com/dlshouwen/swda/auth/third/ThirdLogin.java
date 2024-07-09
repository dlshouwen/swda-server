package com.dlshouwen.swda.auth.third;

import lombok.Data;

import java.io.Serializable;

/**
 * third login
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class ThirdLogin implements Serializable {
	
	/** seral version uid */
	private static final long serialVersionUID = 1L;

	/** open type */
	private Integer openType;

	/** code */
	private String code;

	/** state */
	private String state;

}
