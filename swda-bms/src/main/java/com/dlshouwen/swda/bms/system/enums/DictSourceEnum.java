package com.dlshouwen.swda.bms.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * dict source
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum DictSourceEnum {

	DICT(0),

	SQL(1);

	/** value */
	private final int value;

}
