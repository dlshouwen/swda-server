package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict call_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "调用类型")
public class CallType {

	@Schema(description= "JDBC Template")
	public static Integer JDBC_TEMPLATE = 1;

	@Schema(description= "MyBatis")
	public static Integer MYBATIS = 2;

}