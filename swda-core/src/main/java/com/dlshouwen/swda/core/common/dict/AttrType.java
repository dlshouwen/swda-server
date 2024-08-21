package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict attr_type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "参数类型")
public interface AttrType {

	@Schema(description = "系统参数")
	int SYSTEM = 1;

	@Schema(description = "页面参数")
	int PAGE = 2;

	@Schema(description = "基础参数")
	int BASE = 3;

	@Schema(description = "日志参数")
	int LOG = 4;

	@Schema(description = "接口参数")
	int INTERFACE = 5;

}