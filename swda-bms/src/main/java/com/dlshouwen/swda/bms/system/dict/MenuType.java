package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict menu_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "菜单类型")
public interface MenuType {

	@Schema(description = "菜单")
	String MENU = "1";

	@Schema(description = "按钮")
	String BUTTON = "2";

}