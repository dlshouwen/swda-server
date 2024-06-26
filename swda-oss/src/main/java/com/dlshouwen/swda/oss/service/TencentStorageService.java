package com.dlshouwen.swda.oss.service;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.oss.properties.StorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * tencent storage service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class TencentStorageService extends StorageService {

	/** cos credentials */
	private final COSCredentials cred;

	/** client config */
	private final ClientConfig clientConfig;

	/**
	 * constructor
	 * @param properties
	 */
	public TencentStorageService(StorageProperties properties) {
//		set properties
		this.properties = properties;
//		set cos credentials
		cred = new BasicCOSCredentials(properties.getTencent().getAccessKey(), properties.getTencent().getSecretKey());
//		create client config
		clientConfig = new ClientConfig(new Region(properties.getTencent().getRegion()));
//		set http protocol
		clientConfig.setHttpProtocol(HttpProtocol.https);
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
	 * uplaod
	 * @param inputStream
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
//		try catch
		try {
//			create cos client
			COSClient cosClient = new COSClient(cred, clientConfig);
//			create metadata
			ObjectMetadata metadata = new ObjectMetadata();
//			set content length
			metadata.setContentLength(inputStream.available());
//			create request
			PutObjectRequest request = new PutObjectRequest(properties.getTencent().getBucketName(), path, inputStream,
					metadata);
//			do put
			PutObjectResult result = cosClient.putObject(request);
//			shut down
			cosClient.shutdown();
//			if etag is empty
			if (result.getETag() == null) {
//				throw exception
				throw new SwdaException("上传文件失败，请检查配置信息");
			}
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
//		return url
		return properties.getConfig().getDomain() + "/" + path;
	}

}