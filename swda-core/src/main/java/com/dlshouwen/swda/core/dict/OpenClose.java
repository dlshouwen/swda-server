package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open_close
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "开启关闭")
public class OpenClose {

	@Schema(description= "启用")
	public static Integer OPEN = 1;

	@Schema(description= "禁用")
	public static Integer CLOSE = 0;

}