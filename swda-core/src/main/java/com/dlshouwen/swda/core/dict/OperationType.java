package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_type
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "操作类型")
public class OperationType {

	@Schema(description= "未知")
	public static Integer UNKNOWN = 0;

	@Schema(description= "查询")
	public static Integer SELECT = 1;

	@Schema(description= "新增")
	public static Integer INSERT = 2;

	@Schema(description= "更新")
	public static Integer UPDATE = 3;

	@Schema(description= "删除")
	public static Integer DELETE = 4;

	@Schema(description= "登录")
	public static Integer EXPORT = 5;

	@Schema(description= "登出")
	public static Integer IMPORT = 6;
	
	@Schema(description= "登录")
	public static Integer LOGIN = 7;
	
	@Schema(description= "登出")
	public static Integer LOGOUT = 8;
	
	@Schema(description= "登出")
	public static Integer OTHER = 9;

}