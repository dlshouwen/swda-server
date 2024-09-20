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
	int UNKNOWN = 0;

	@Schema(description = "select")
	int SELECT = 1;

	@Schema(description = "insert")
	int INSERT = 2;

	@Schema(description = "update")
	int UPDATE = 3;

	@Schema(description = "delete")
	int DELETE = 4;

	@Schema(description = "export")
	int EXPORT = 5;

	@Schema(description = "import")
	int IMPORT = 6;
	
	@Schema(description = "login")
	int LOGIN = 7;
	
	@Schema(description = "logout")
	int LOGOUT = 8;
	
	@Schema(description = "other")
	int OTHER = 9;

}