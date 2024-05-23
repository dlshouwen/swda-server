package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict operation_type
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="操作类型", description="操作类型")
public class OperationType {

	@Schema(title="未知")
	public static String UNKNOWN = "0";

	@Schema(title="查询")
	public static String SELECT = "1";

	@Schema(title="新增")
	public static String INSERT = "2";

	@Schema(title="更新")
	public static String UPDATE = "3";

	@Schema(title="删除")
	public static String DELETE = "4";

	@Schema(title="登录")
	public static String LOGIN = "5";

	@Schema(title="登出")
	public static String LOGOUT = "6";

}