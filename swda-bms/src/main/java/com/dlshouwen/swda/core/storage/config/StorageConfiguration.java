package com.dlshouwen.swda.core.storage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlshouwen.swda.core.storage.enums.StorageTypeEnum;
import com.dlshouwen.swda.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.core.storage.strategy.AliyunStorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.HuaweiStorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.LocalStorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.MinioStorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.QiniuStorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.StorageStrategy;
import com.dlshouwen.swda.core.storage.strategy.TencentStorageStrategy;

/**
 * storage configuration
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(prefix = "storage", value = "enabled")
public class StorageConfiguration {

	/**
	 * storage service
	 * @param properties
	 * @return storage service
	 */
	@Bean
	public StorageStrategy storageService(StorageProperties properties) {
//		regist storage service
		if (properties.getConfig().getType().equals(StorageTypeEnum.LOCAL)) {
			return new LocalStorageStrategy(properties);
		} else if (properties.getConfig().getType().equals(StorageTypeEnum.ALIYUN)) {
			return new AliyunStorageStrategy(properties);
		} else if (properties.getConfig().getType().equals(StorageTypeEnum.TENCENT)) {
			return new TencentStorageStrategy(properties);
		} else if (properties.getConfig().getType().equals(StorageTypeEnum.QINIU)) {
			return new QiniuStorageStrategy(properties);
		} else if (properties.getConfig().getType().equals(StorageTypeEnum.HUAWEI)) {
			return new HuaweiStorageStrategy(properties);
		} else if (properties.getConfig().getType().equals(StorageTypeEnum.MINIO)) {
			return new MinioStorageStrategy(properties);
		}
		return null;
	}

}
