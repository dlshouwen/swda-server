package com.dlshouwen.swda.bms.platform.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict open type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "open type")
public interface OpenType {

	@Schema(description = "wechat open")
	int WECHAT_OPEN = 1;

	@Schema(description = "wecha work")
	int WECHAT_WORK = 2;

	@Schema(description = "ding talk")
	int DING_TALK = 3;

	@Schema(description = "fei shu")
	int FEI_SHU = 4;

}