package com.dlshouwen.swda.bms.oss.properties;

import lombok.Data;

/**
 * tencent storage properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class TencentStorageProperties {
	
	private String accessKey;
	
	private String secretKey;
	
	private String region;
	
	private String bucketName;

}
