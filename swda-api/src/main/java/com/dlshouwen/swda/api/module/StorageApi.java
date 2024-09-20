package com.dlshouwen.swda.api.module;

import java.io.InputStream;

/**
 * storage api
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface StorageApi {

	/**
	 * get new file name
	 * @param fileName
	 * @return new file name
	 */
	String getNewFileName(String fileName);

	/**
	 * get path
	 * @return path
	 */
	String getPath();

	/**
	 * get path
	 * @param fileName
	 * @return path
	 */
	String getPath(String fileName);

	/**
	 * upload
	 * @param data
	 * @param path
	 * @return url
	 */
	String upload(byte[] data, String path);

	/**
	 * upload
	 * @param inputStream
	 * @param path
	 * @return url
	 */
	String upload(InputStream inputStream, String path);

}