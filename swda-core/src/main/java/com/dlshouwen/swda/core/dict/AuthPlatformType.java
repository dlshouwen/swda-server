package com.dlshouwen.swda.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict auth platform type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "auth platform type")
public interface AuthPlatformType {

	@Schema(description = "wechat open")
	int WECHAT_OPEN = 1;

	@Schema(description = "wecha work")
	int WECHAT_WORK = 2;

	@Schema(description = "ding talk")
	int DING_TALK = 3;

	@Schema(description = "fei shu")
	int FEI_SHU = 4;

}