package com.dlshouwen.swda.oss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dlshouwen.swda.oss.enums.StorageTypeEnum;
import com.dlshouwen.swda.oss.properties.StorageProperties;
import com.dlshouwen.swda.oss.service.AliyunStorageService;
import com.dlshouwen.swda.oss.service.HuaweiStorageService;
import com.dlshouwen.swda.oss.service.LocalStorageService;
import com.dlshouwen.swda.oss.service.MinioStorageService;
import com.dlshouwen.swda.oss.service.QiniuStorageService;
import com.dlshouwen.swda.oss.service.StorageService;
import com.dlshouwen.swda.oss.service.TencentStorageService;

/**
 * storage configuration
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
