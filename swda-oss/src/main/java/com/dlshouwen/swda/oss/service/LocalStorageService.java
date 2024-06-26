package com.dlshouwen.swda.oss.service;

import org.springframework.util.FileCopyUtils;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.oss.properties.StorageProperties;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * local storage service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class LocalStorageService extends StorageService {

	/**
	 * constructor
	 * @param properties
	 */
	public LocalStorageService(StorageProperties properties) {
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
//		try catch
		try {
//			create file
			File file = new File(properties.getLocal().getPath() + File.separator + path);
//			get folder
			File folder = file.getParentFile();
//			if folder is file
			if (folder != null && !folder.mkdirs() && !folder.isDirectory()) {
//				throw exception
				throw new IOException("目录 '" + folder + "' 创建失败");
			}
//			copy file
			FileCopyUtils.copy(inputStream, Files.newOutputStream(file.toPath()));
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("上传文件失败：", e);
		}
//		return url
		return properties.getConfig().getDomain() + "/" + properties.getLocal().getUrl() + "/" + path;
	}

}
