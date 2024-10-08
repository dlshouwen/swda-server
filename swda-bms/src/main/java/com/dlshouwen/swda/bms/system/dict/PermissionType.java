package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict function_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "功能类型")
public interface PermissionType {

	@Schema(description = "菜单")
	int MENU = 1;

	@Schema(description = "按钮")
	int BUTTON = 2;

}