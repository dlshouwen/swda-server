package com.dlshouwen.swda.core.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class DateUtils {
	
	/** date pattern */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	
	/** date time pattern */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * format
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	/**
	 * format
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * parse
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
