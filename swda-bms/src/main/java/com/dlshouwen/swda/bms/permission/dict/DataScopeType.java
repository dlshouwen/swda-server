package com.dlshouwen.swda.bms.permission.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict data scope type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "data scope type")
public interface DataScopeType {
	
	@Schema(description = "all")
	String ALL = "0";

	@Schema(description = "organ and children")
	String ORGAN_AND_CHILDREN = "1";
	
	@Schema(description = "organ only")
	String ORGAN_ONLY = "1";
	
	@Schema(description = "self")
	String SELF = "1";

	@Schema(description = "custom")
	String CUSTOM = "2";

}