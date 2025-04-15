package com.dlshouwen.swda.core.storage.strategy;

import com.dlshouwen.swda.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.obs.services.ObsClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * huawei storage strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class HuaweiStorageStrategy extends StorageStrategy {

	/**
	 * huawei storage strategy
	 * @param properties
	 */
	public HuaweiStorageStrategy(StorageProperties properties) {
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
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
//		create obs client
		ObsClient client = new ObsClient(properties.getHuawei().getAccessKey(), properties.getHuawei().getSecretKey(), properties.getHuawei().getEndPoint());
//		try catch
		try {
//			do put
			client.putObject(properties.getHuawei().getBucketName(), path, inputStream);
//			close client
			client.close();
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
//		return url
		return properties.getConfig().getDomain() + "/" + path;
	}

}