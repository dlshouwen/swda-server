package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict logout_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "登出类型")
public class LogoutType {

	@Schema(description= "正常退出")
	public static Integer NORMAL = 1;

	@Schema(description= "TOKEN失效退出")
	public static Integer TOKEN_INVALID = 2;

	@Schema(description= "强制下线")
	public static Integer FORCE_OUTLINE = 3;

}