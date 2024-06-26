package com.dlshouwen.swda.oss.properties;

import lombok.Data;

/**
 * minio storage properties
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class MinioStorageProperties {
	
	private String endPoint;
	
	private String accessKey;
	
	private String secretKey;
	
	private String bucketName;

}