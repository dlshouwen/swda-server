package com.dlshouwen.swda.bms.core.storage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlshouwen.swda.bms.core.storage.enums.StorageTypeEnum;
import com.dlshouwen.swda.bms.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.bms.core.storage.strategy.AliyunStorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.HuaweiStorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.LocalStorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.MinioStorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.QiniuStorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.StorageStrategy;
import com.dlshouwen.swda.bms.core.storage.strategy.TencentStorageStrategy;

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
		if (properties.getConfig().getType() == StorageTypeEnum.LOCAL) {
			return new LocalStorageStrategy(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.ALIYUN) {
			return new AliyunStorageStrategy(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.TENCENT) {
			return new TencentStorageStrategy(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.QINIU) {
			return new QiniuStorageStrategy(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.HUAWEI) {
			return new HuaweiStorageStrategy(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.MINIO) {
			return new MinioStorageStrategy(properties);
		}
		return null;
	}

}