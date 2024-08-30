package com.dlshouwen.swda.bms.oss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlshouwen.swda.bms.oss.enums.StorageTypeEnum;
import com.dlshouwen.swda.bms.oss.properties.StorageProperties;
import com.dlshouwen.swda.bms.oss.strategy.AliyunStorageService;
import com.dlshouwen.swda.bms.oss.strategy.HuaweiStorageService;
import com.dlshouwen.swda.bms.oss.strategy.LocalStorageService;
import com.dlshouwen.swda.bms.oss.strategy.MinioStorageService;
import com.dlshouwen.swda.bms.oss.strategy.QiniuStorageService;
import com.dlshouwen.swda.bms.oss.strategy.StorageService;
import com.dlshouwen.swda.bms.oss.strategy.TencentStorageService;

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
	public StorageService storageService(StorageProperties properties) {
//		regist storage service
		if (properties.getConfig().getType() == StorageTypeEnum.LOCAL) {
			return new LocalStorageService(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.ALIYUN) {
			return new AliyunStorageService(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.TENCENT) {
			return new TencentStorageService(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.QINIU) {
			return new QiniuStorageService(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.HUAWEI) {
			return new HuaweiStorageService(properties);
		} else if (properties.getConfig().getType() == StorageTypeEnum.MINIO) {
			return new MinioStorageService(properties);
		}
		return null;
	}

}
