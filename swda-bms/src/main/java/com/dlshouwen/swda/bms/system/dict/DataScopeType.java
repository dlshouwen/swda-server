package com.dlshouwen.swda.bms.system.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict data scope type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "data scope type")
public interface DataScopeType {
	
	@Schema(description = "all")
	int ALL = 0;

	@Schema(description = "organ and children")
	int ORGAN_AND_CHILDREN = 1;
	
	@Schema(description = "organ only")
	int ORGAN_ONLY = 1;
	
	@Schema(description = "self")
	int SELF = 1;

	@Schema(description = "custom")
	int CUSTOM = 2;

}