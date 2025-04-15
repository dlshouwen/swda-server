package com.dlshouwen.swda.core.base.utils;

import cn.hutool.core.io.IoUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * exception utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ExceptionUtils {

	/**
	 * to string
	 * @param e
	 * @return
	 */
	public static String toString(Throwable e) {
//		create string write
		StringWriter stringWriter = new StringWriter();
//		create print writer
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
//		print to writer
		e.printStackTrace(printWriter);
//		close writer
		IoUtil.close(printWriter);
		IoUtil.close(stringWriter);
//		return data
		return stringWriter.toString();
	}

}