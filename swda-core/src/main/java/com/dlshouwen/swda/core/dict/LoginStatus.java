package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author 大连首闻科技有限公司
 * @since 1.0
 */
@Schema(description = "登录状态")
public class LoginStatus {

	@Schema(description= "登录成功")
	public static Integer SUCCESS = 1;

	@Schema(description= "用户不存在")
	public static Integer USER_NOT_EXIST = 2;

	@Schema(description= "密码错误")
	public static Integer PASSWORD_ERROR = 3;

	@Schema(description= "用户无效")
	public static Integer USER_INVALID = 4;

	@Schema(description= "用户无权限")
	public static Integer NO_LIMIT = 5;

}