package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open_close
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "开启关闭")
public interface OpenClose {

	@Schema(description = "启用")
	int OPEN = 1;

	@Schema(description = "禁用")
	int CLOSE = 0;

}