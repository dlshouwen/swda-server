package com.dlshouwen.swda.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * file utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class FileUtils {
	
	/**
	 * get file full name
	 * @param fullName
	 * @return file name
	 */
	public static String getFileFullName(String fullName){
		if(!fullName.contains(File.separator)) {
			return fullName;
		}
		return fullName.substring(fullName.lastIndexOf(File.separator)+1);
	}
	
	/**
	 * get file name
	 * @param fullName
	 * @param isDirectory
	 * @return file name
	 */
	public static String getFileName(String fullName, boolean isDirectory){
		String fileName;
		if(isDirectory){
			fileName = fullName.substring(fullName.lastIndexOf(File.separator)+1);
		}else{
			fileName = fullName.substring(fullName.lastIndexOf(File.separator)+1, fullName.lastIndexOf("."));
		}
		return fileName;
	}
	
	/**
	 * get file extension
	 * @param fullName
	 * @return file extension
	 */
	public static String getFileExtension(String fullName){
		return fullName.substring(fullName.lastIndexOf(".")+1);
	}
	
	/**
	 * get content type
	 * @param fileName
	 * @return content type
	 */
	public static String getContentType(String fileName) {
		fileName = fileName.toLowerCase();
		String contentType = "";
		if(fileName.endsWith("txt")) contentType="text/plain";
		if(fileName.endsWith("gif")) contentType="image/gif";
		if(fileName.endsWith("jpg")) contentType="image/jpeg";
		if(fileName.endsWith("jpeg")) contentType="image/jpeg";
		if(fileName.endsWith("jpe")) contentType="image/jpeg";
		if(fileName.endsWith("zip")) contentType="application/zip";
		if(fileName.endsWith("rar")) contentType="application/rar";
		if(fileName.endsWith("doc")) contentType="application/msword";
		if(fileName.endsWith("ppt")) contentType="application/vnd.ms-powerpoint";
		if(fileName.endsWith("xls")) contentType="application/vnd.ms-excel";
		if(fileName.endsWith("html")) contentType="text/html";
		if(fileName.endsWith("htm")) contentType="text/html";
		if(fileName.endsWith("tif")) contentType="image/tiff";
		if(fileName.endsWith("tiff")) contentType="image/tiff";
		if(fileName.endsWith("pdf")) contentType="application/pdf";
		return contentType;
	}
	
	/**
	 * get folder time
	 * @param date
	 * @return folder time
	 */
	public static String getFolderTime(Date date){
		return new SimpleDateFormat("yyyyMM").format(date);
	}
	
}
