package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "操作类型")
public interface OperationType {

	@Schema(description = "未知")
	int UNKNOWN = 0;

	@Schema(description = "查询")
	int SELECT = 1;

	@Schema(description = "新增")
	int INSERT = 2;

	@Schema(description = "更新")
	int UPDATE = 3;

	@Schema(description = "删除")
	int DELETE = 4;

	@Schema(description = "登录")
	int EXPORT = 5;

	@Schema(description = "登出")
	int IMPORT = 6;
	
	@Schema(description = "登录")
	int LOGIN = 7;
	
	@Schema(description = "登出")
	int LOGOUT = 8;
	
	@Schema(description = "登出")
	int OTHER = 9;

}