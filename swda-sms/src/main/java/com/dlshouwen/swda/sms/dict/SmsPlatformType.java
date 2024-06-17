package com.dlshouwen.swda.sms.dict;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * dict sms platform type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Schema(description = "短信平台类型")
public class SmsPlatformType {

	@Schema(description= "阿里云")
	public static Integer ALIYUN = 1;

	@Schema(description= "腾讯云")
	public static Integer TENCENT = 2;
	
	@Schema(description= "七牛云")
	public static Integer QINIU = 3;
	
	@Schema(description= "华为云")
	public static Integer HUAWEI = 4;

}