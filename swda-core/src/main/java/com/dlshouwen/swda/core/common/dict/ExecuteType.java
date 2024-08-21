package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict execute_type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "执行类型")
public interface ExecuteType {

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

}