package com.dlshouwen.swda.core.storage.strategy;

import org.springframework.util.FileCopyUtils;

import com.dlshouwen.swda.core.storage.properties.StorageProperties;
import com.dlshouwen.swda.core.base.exception.SwdaException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * local storage strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class LocalStorageStrategy extends StorageStrategy {

	/**
	 * constructor
	 * @param properties
	 */
	public LocalStorageStrategy(StorageProperties properties) {
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
