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
	String SYSTEM = "1";

	@Schema(description = "page")
	String PAGE = "2";

	@Schema(description = "base")
	String BASE = "3";

	@Schema(description = "log")
	String LOG = "4";

	@Schema(description = "interface")
	String INTERFACE = "5";

}