package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "operation type")
public interface OperationType {

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

	@Schema(description = "export")
	String EXPORT = "5";

	@Schema(description = "import")
	String IMPORT = "6";
	
	@Schema(description = "login")
	String LOGIN = "7";
	
	@Schema(description = "logout")
	String LOGOUT = "8";
	
	@Schema(description = "other")
	String OTHER = "9";

}