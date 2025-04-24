package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict menu_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "menu type")
public interface MenuType {

	@Schema(description = "menu")
	String MENU = "1";

	@Schema(description = "button")
	String BUTTON = "2";

}