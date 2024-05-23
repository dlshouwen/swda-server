package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict logout_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="登出类型", description="登出类型")
public class LogoutType {

	@Schema(title="正常退出")
	public static String NORMAL = "1";

	@Schema(title="TOKEN失效退出")
	public static String TOKEN_INVALID = "2";

	@Schema(title="强制下线")
	public static String FORCE_OUTLINE = "3";

}