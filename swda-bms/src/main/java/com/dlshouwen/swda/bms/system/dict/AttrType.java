package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict attr_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "attr type")
public interface AttrType {

	@Schema(description = "system")
	int SYSTEM = 1;

	@Schema(description = "page")
	int PAGE = 2;

	@Schema(description = "base")
	int BASE = 3;

	@Schema(description = "log")
	int LOG = 4;

	@Schema(description = "interface")
	int INTERFACE = 5;

}