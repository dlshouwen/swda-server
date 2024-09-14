package com.dlshouwen.swda.bms.platform.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sms platform type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Schema(description = "短信平台类型")
public interface SmsPlatformType {

	@Schema(description= "阿里云")
	Integer ALIYUN = 1;

	@Schema(description= "腾讯云")
	Integer TENCENT = 2;
	
	@Schema(description= "七牛云")
	Integer QINIU = 3;
	
	@Schema(description= "华为云")
	Integer HUAWEI = 4;

}