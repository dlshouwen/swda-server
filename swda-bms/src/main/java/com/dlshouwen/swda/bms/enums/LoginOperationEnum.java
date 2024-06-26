package com.dlshouwen.swda.bms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * login operation
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum LoginOperationEnum {

	LOGIN_SUCCESS(0),

	LOGOUT_SUCCESS(1),

	CAPTCHA_FAIL(2),

	ACCOUNT_FAIL(3);

	/** value */
	private final int value;

}
