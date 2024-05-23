package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict permission_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="权限类型", description="权限类型")
public class PermissionType {

	@Schema(title="菜单")
	public static String MENU = "1";

	@Schema(title="按钮")
	public static String BUTTON = "2";

}