package com.dlshouwen.swda.auth.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * third login
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ThirdLoginEnum {

	WECHAT_WORK("wechat_work"),

	DING_TALK("dingtalk"),
	
	FEI_SHU("feishu"),

	WECHAT_OPEN("wechat_open");

	/** value */
	private final String value;

	/**
	 * to enum
	 * @param value
	 * @return enum
	 */
	public static ThirdLoginEnum toEnum(String value) {
		for (ThirdLoginEnum item : values()) {
			if (StrUtil.equalsIgnoreCase(item.getValue(), value)) {
				return item;
			}
		}
		throw new IllegalArgumentException("Unsupported third login type: " + value);
	}

}