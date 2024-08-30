package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "调用类型")
public interface CallType {

	@Schema(description = "JDBC Template")
	int JDBC_TEMPLATE = 1;

	@Schema(description = "MyBatis")
	int MYBATIS = 2;

}