package com.dlshouwen.swda.bms.system.utils;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * cron utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class CronUtils {

	/**
	 * is valid
	 * @params cornExpressio
	 * @return cron expression is valid
	 */
	public static boolean isValid(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}

	/**
	 * get next execution
	 * @params cornExpressio
	 * @return next execute date
	 */
	public static Date getNextExecution(String cronExpression) {
//    	try catch
		try {
//        	get core expression
			CronExpression cron = new CronExpression(cronExpression);
//			get next valid time after
			return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
