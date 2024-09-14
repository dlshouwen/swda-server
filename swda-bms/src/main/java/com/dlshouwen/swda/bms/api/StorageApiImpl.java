package com.dlshouwen.swda.bms.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.api.module.StorageApi;
import com.dlshouwen.swda.bms.core.storage.strategy.StorageStrategy;

import java.io.InputStream;

/**
 * storage api impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Component
@AllArgsConstructor
public class StorageApiImpl implements StorageApi {

	/** storage strategy */
	private final StorageStrategy storageStrategy;

	/**
	 * get new file name
	 * @param fileName
	 * @return new file name
	 */
	@Override
	public String getNewFileName(String fileName) {
		return storageStrategy.getNewFileName(fileName);
	}

	/**
	 * get path
	 * @return path
	 */
	@Override
	public String getPath() {
		return storageStrategy.getPath();
	}

	/**
	 * get path
	 * @param fileName
	 * @return path
	 */
	@Override
	public String getPath(String fileName) {
		return storageStrategy.getPath(fileName);
	}

	/**
	 * upload
	 * @param data
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(byte[] data, String path) {
		return storageStrategy.upload(data, path);
	}

	/**
	 * upload
	 * @param inputStream
	 * @param path
	 * @return url
	 */
	@Override
	public String upload(InputStream inputStream, String path) {
		return storageStrategy.upload(inputStream, path);
	}

}
