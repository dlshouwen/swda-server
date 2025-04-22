package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict execute_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "execute type")
public interface ExecuteType {

	@Schema(description = "unknow")
	String UNKNOWN = "0";

	@Schema(description = "select")
	String SELECT = "1";

	@Schema(description = "insert")
	String INSERT = "2";

	@Schema(description = "update")
	String UPDATE = "3";

	@Schema(description = "delete")
	String DELETE = "4";

}