package com.dlshouwen.swda.bms.core.storage.strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import org.springframework.util.StringUtils;

import com.dlshouwen.swda.bms.core.storage.properties.StorageProperties;

import java.io.InputStream;
import java.util.Date;

/**
 * storage strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public abstract class StorageStrategy {
	
	/** storage properties */
	public StorageProperties properties;

	/**
	 * get new file name
	 * @param fileName
	 * @return new file name
	 */
	public String getNewFileName(String fileName) {
//		get file prefix
		String prefix = FileNameUtil.getPrefix(fileName);
//		get file suffix
		String suffix = FileNameUtil.getSuffix(fileName);
//		get second time
		long time = DateUtil.timeToSecond(DateUtil.formatTime(new Date()));
//		return new file name
		return prefix + "_" + time + "." + suffix;
	}

	/**
	 * get path
	 * @return path
	 */
	public String getPath() {
//		get path by date
		String path = DateUtil.format(new Date(), "yyyyMMdd");
//		if has prrefix
		if (StringUtils.hasText(properties.getConfig().getPrefix())) {
//			add prefix to path
			path = properties.getConfig().getPrefix() + "/" + path;
		}
//		return path
		return path;
	}

	/**
	 * get path
	 * @param fileName
	 * @return path
	 */
	public String getPath(String fileName) {
		return getPath() + "/" + getNewFileName(fileName);
	}

	/**
	 * upload
	 * @param data
	 * @param path
	 * @return url
	 */
	public abstract String upload(byte[] data, String path);

	/**
	 * upload
	 * @param inputStream
	 * @param path
	 * @return url
	 */
	public abstract String upload(InputStream inputStream, String path);

}
