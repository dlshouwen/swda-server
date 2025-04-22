package com.dlshouwen.swda.bms.permission.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict gender
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "性别")
public interface Gender {

	@Schema(description = "男")
	String MALE = "1";

	@Schema(description = "女")
	String FEMALE = "2";

	@Schema(description = "未知")
	String UNKNOWN = "9";

}