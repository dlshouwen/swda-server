package com.dlshouwen.swda.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * super admin
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum SuperAdminEnum {

	YES(1, "是"),

	NO(0, "否");

	/** value */
	private final Integer value;
	/** name */
	private final String name;

	/**
	 * get name by value
	 * @param value
	 * @return name
	 */
	public static String getNameByValue(int value) {
		for (SuperAdminEnum s : SuperAdminEnum.values()) {
			if (s.getValue() == value) {
				return s.getName();
			}
		}
		return "";
	}

	/**
	 * get value by name
	 * @param name
	 * @return value
	 */
	public static Integer getValueByName(String name) {
		for (SuperAdminEnum s : SuperAdminEnum.values()) {
			if (Objects.equals(s.getName(), name)) {
				return s.getValue();
			}
		}
		return null;
	}

}