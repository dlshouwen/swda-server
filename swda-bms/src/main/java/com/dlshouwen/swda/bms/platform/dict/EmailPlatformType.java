package com.dlshouwen.swda.bms.platform.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict email platform type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "email platform type")
public interface EmailPlatformType {
	
	@Schema(description= "local")
	String LOCAL = "0";

	@Schema(description= "aliyun")
	String ALIYUN = "1";

}