package com.dlshouwen.swda.core.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * operation type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum OperateType {

	UNKNOW("0"),

	SEARCH("1"),

	INSERT("2"),

	UPDATE("3"),

	DELETE("4"),

	EXPORT("5"),

	IMPORT("6"),

	LOGIN("7"),

	LOGOUT("8"),

	OTHER("9");

	/** value */
	private final String value;

}
