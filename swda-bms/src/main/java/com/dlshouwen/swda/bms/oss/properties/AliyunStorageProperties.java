package com.dlshouwen.swda.bms.oss.properties;

import lombok.Data;

/**
 * aliyun storage properties
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class AliyunStorageProperties {

	private String endPoint;

	private String accessKeyId;

	private String accessKeySecret;

	private String bucketName;

}
