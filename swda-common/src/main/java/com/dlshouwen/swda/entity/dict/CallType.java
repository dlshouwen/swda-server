package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="调用类型", description="调用类型")
public class CallType {

	@Schema(title="JDBC Template")
	public static String JDBC_TEMPLATE = "1";

	@Schema(title="MyBatis")
	public static String MYBATIS = "2";

}