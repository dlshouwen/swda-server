package com.dlshouwen.swda.entity.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author liujingcheng@live.cn
 * @since 1.0
 */
@Schema(title="登录状态", description="登录状态")
public class LoginStatus {

	@Schema(title="登录成功")
	public static String SUCCESS = "1";

	@Schema(title="用户不存在")
	public static String USER_NOT_EXIST = "2";

	@Schema(title="密码错误")
	public static String PASSWORD_ERROR = "3";

	@Schema(title="用户无效")
	public static String USER_INVALID = "4";

	@Schema(title="用户无权限")
	public static String NO_LIMIT = "5";

	@Schema(title="验证码不正确")
	public static String VALID_CODE_ERROR = "6";

}