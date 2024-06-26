package com.dlshouwen.swda.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.dlshouwen.swda.oss.enums.StorageTypeEnum;

/**
 * storage properties
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	
	/** enabled */
	private boolean enabled;
	
	/** config */
	private StorageConfig config;
	
	/** local storage properties */
	private LocalStorageProperties local;
	
	/** aliyun storage properties */
	private AliyunStorageProperties aliyun;
	
	/** qiniu storage properties */
	private QiniuStorageProperties qiniu;
	
	/** huawei storage properties */
	private HuaweiStorageProperties huawei;
	
	/** minio storage properties */
	private MinioStorageProperties minio;
	
	/** tencent storage properties */
	private TencentStorageProperties tencent;

	/**
	 * storage config
	 * @author liujingcheng@live.cn
	 * @since 1.0.0
	 */
	@Data
	public static class StorageConfig {
		
		/** domain */
		private String domain;
		
		/** prefix */
		private String prefix;
		
		/** type */
		private StorageTypeEnum type;

	}

	/**
	 * local storage properties
	 * @return local storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.local")
	public LocalStorageProperties localStorageProperties() {
		return new LocalStorageProperties();
	}

	/**
	 * aliyun storage properties
	 * @return aliyun storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.aliyun")
	public AliyunStorageProperties aliyunStorageProperties() {
		return new AliyunStorageProperties();
	}

	/**
	 * qiniu storage properties
	 * @return qiniu storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.qiniu")
	public QiniuStorageProperties qiniuStorageProperties() {
		return new QiniuStorageProperties();
	}

	/**
	 * huawei storage properties
	 * @return huawei storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.huawei")
	public HuaweiStorageProperties huaweiStorageProperties() {
		return new HuaweiStorageProperties();
	}

	/**
	 * minio storage properties
	 * @return minio storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.minio")
	public MinioStorageProperties minioStorageProperties() {
		return new MinioStorageProperties();
	}

	/**
	 * tencent storage properties
	 * @return tencent storage properties
	 */
	@Bean
	@ConfigurationProperties(prefix = "storage.tencent")
	public TencentStorageProperties tencentStorageProperties() {
		return new TencentStorageProperties();
	}

}
