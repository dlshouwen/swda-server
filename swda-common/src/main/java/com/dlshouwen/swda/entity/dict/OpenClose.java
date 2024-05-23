package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open_close
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="开启关闭", description="开启关闭")
public class OpenClose {

	@Schema(title="启用")
	public static String OPEN = "1";

	@Schema(title="禁用")
	public static String CLOSE = "0";

}