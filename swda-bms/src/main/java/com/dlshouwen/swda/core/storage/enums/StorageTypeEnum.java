package com.dlshouwen.swda.bms.core.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * storage type enum
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {

	LOCAL(0),

	ALIYUN(1),

	TENCENT(2),

	QINIU(3),

	HUAWEI(4),

	MINIO(5);
	
	private final Integer value;

}