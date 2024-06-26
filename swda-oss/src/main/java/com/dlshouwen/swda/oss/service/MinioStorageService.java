package com.dlshouwen.swda.oss.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.oss.properties.StorageProperties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

/**
 * minio storage service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class MinioStorageService extends StorageService {
	
	/** minio client */
	private final MinioClient minioClient;

	/**
	 * constructor
	 * @param properties
	 */
	public MinioStorageService(StorageProperties properties) {
//		set properties
		this.properties = properties;
//		build monio client
		minioClient = MinioClient.builder().endpoint(properties.getMinio().getEndPoint()).credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey()).build();
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
//		try catch
		try {
//			get bucket is exists
			boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getMinio().getBucketName()).build());
//			if not found
			if (!found) {
//				create bucket
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getMinio().getBucketName()).build());
			}
//			set content type
			String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//			get media type
			Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
//			is present
			if (mediaType.isPresent()) {
//				set content type from media type
				contentType = mediaType.get().toString();
			}
//			do put
			minioClient.putObject(PutObjectArgs.builder().bucket(properties.getMinio().getBucketName()).contentType(contentType).object(path).stream(inputStream, inputStream.available(), -1).build());
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
//		return url
		return properties.getMinio().getEndPoint() + "/" + properties.getMinio().getBucketName() + "/" + path;
	}

}