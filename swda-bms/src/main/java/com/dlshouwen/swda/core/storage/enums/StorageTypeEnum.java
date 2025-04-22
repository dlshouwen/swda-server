package com.dlshouwen.swda.core.storage.enums;

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

	LOCAL("local"),

	ALIYUN("aliyun"),

	TENCENT("tencent"),

	QINIU("qinue"),

	HUAWEI("huawei"),

	MINIO("minio");
	
	private final String value;

}