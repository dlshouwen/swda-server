package com.dlshouwen.swda.core.common.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict gender
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "性别")
public interface Gender {

	@Schema(description = "男")
	int MALE = 1;

	@Schema(description = "女")
	int FEMALE = 2;

	@Schema(description = "未知")
	int UNKNOWN = 9;

}