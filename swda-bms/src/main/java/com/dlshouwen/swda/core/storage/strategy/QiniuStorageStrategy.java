package com.dlshouwen.swda.core.storage.strategy;

import com.dlshouwen.swda.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * qiniu storage strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class QiniuStorageStrategy extends StorageStrategy {
	
	/** upload manager */
	private final UploadManager uploadManager;

	/**
	 * constructor
	 * @param properties
	 */
	public QiniuStorageStrategy(StorageProperties properties) {
//		set properties
		this.properties = properties;
//		create upload manager
		uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
	}

	/**
	 * upload
	 * @param data
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(byte[] data, String path) {
//		try catch
		try {
//			get token
			String token = Auth.create(properties.getQiniu().getAccessKey(), properties.getQiniu().getSecretKey()).uploadToken(properties.getQiniu().getBucketName());
//			do put
			Response res = uploadManager.put(data, path, token);
//			if not success
			if (!res.isOK()) {
//				throw exception
				throw new SwdaException(res.toString());
			}
//			return url
			return properties.getConfig().getDomain() + "/" + path;
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
	}

	/**
	 * upload
	 * @param inputSream
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
//		try catch
		try {
//			convert input stream to byte array
			byte[] data = IOUtils.toByteArray(inputStream);
//			upload
			return this.upload(data, path);
		} catch (IOException e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
	}

}
