package com.dlshouwen.swda.oss.properties;

import lombok.Data;

/**
 * qiniu storage properties
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class QiniuStorageProperties {

	private String accessKey;
	
	private String secretKey;
	
	private String bucketName;

}
