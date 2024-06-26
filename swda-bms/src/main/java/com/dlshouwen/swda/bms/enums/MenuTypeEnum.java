package com.dlshouwen.swda.bms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * menu type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

	MENU(0),

	BUTTON(1),

	INTERFACE(2);

	/** value */
	private final int value;

}
