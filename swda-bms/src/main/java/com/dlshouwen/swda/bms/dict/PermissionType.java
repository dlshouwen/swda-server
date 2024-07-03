package com.dlshouwen.swda.bms.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict permission type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "permission type")
public interface PermissionType {

	@Schema(description = "menu")
	int MENU = 1;

	@Schema(description = "button")
	int BUTTON = 2;

}