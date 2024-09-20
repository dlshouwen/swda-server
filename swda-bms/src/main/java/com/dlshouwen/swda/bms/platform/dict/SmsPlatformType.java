package com.dlshouwen.swda.bms.platform.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sms platform type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "sms platform type")
public interface SmsPlatformType {

	@Schema(description= "aliyun")
	Integer ALIYUN = 1;

	@Schema(description= "tencent")
	Integer TENCENT = 2;
	
	@Schema(description= "qiniu")
	Integer QINIU = 3;
	
	@Schema(description= "huawei")
	Integer HUAWEI = 4;

}