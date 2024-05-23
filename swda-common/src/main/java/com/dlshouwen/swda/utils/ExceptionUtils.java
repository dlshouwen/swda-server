package com.dlshouwen.swda.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * exception utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ExceptionUtils {

	/**
	 * to string
	 * @param e
	 * @return message
	 */
	public static String toString(Throwable e) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(byteArrayOutputStream);
		e.printStackTrace(ps);
		String message = byteArrayOutputStream.toString();
		ps.close();
		try {
			byteArrayOutputStream.close();
		} catch (Exception ignored) {

		}
		return message;
	}
	
}