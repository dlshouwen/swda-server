package com.dlshouwen.swda.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * data scope
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

	ALL(0),

	ORG_AND_CHILD(1),

	ORG_ONLY(2),

	SELF(3),

	CUSTOM(4);

	private final Integer value;

}