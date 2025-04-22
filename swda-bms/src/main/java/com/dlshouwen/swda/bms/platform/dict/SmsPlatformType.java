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
	String ALIYUN = "1";

	@Schema(description= "tencent")
	String TENCENT = "2";
	
	@Schema(description= "qiniu")
	String QINIU = "3";
	
	@Schema(description= "huawei")
	String HUAWEI = "4";

}