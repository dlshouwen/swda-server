package com.dlshouwen.swda.bms.oss.properties;

import lombok.Data;

/**
 * qiniu storage properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class QiniuStorageProperties {

	private String accessKey;
	
	private String secretKey;
	
	private String bucketName;

}
