package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "call type")
public interface CallType {

	@Schema(description = "JDBC Template")
	String JDBC_TEMPLATE = "1";

	@Schema(description = "MyBatis")
	String MYBATIS = "2";

}