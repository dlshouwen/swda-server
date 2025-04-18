package com.dlshouwen.swda.core.storage.strategy;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.dlshouwen.swda.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.core.base.exception.SwdaException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * aliyun storage strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class AliyunStorageStrategy extends StorageStrategy {

	/**
	 * constructor
	 * @param properties
	 */
	public AliyunStorageStrategy(StorageProperties properties) {
//		set properties
		this.properties = properties;
	}

	/**
	 * upload
	 * @param data
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(byte[] data, String path) {
		return upload(new ByteArrayInputStream(data), path);
	}

	/**
	 * upload
	 * @param inputStream
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
//		create oss client
		OSS client = new OSSClientBuilder().build(properties.getAliyun().getEndPoint(), properties.getAliyun().getAccessKeyId(), properties.getAliyun().getAccessKeySecret());
//		try catch
		try {
//			do put
			client.putObject(properties.getAliyun().getBucketName(), path, inputStream);
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		} finally {
//			shutdown
			if (client != null) {
				client.shutdown();
			}
		}
//		return url
		return properties.getConfig().getDomain() + "/" + path;
	}

}