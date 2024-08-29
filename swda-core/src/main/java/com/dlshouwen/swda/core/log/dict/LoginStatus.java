package com.dlshouwen.swda.core.log.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict login_status
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "登录状态")
public interface LoginStatus {

	@Schema(description = "登录成功")
	int SUCCESS = 1;
	
	@Schema(description = "登录失败")
	int FAILURE = 0;

}