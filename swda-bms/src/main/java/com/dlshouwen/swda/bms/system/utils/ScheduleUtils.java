package com.dlshouwen.swda.bms.system.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.dlshouwen.swda.bms.system.dict.ScheduleJobStatus;
import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
import com.dlshouwen.swda.bms.system.execution.ScheduleConcurrentExecution;
import com.dlshouwen.swda.bms.system.execution.ScheduleDisallowConcurrentExecution;
import com.dlshouwen.swda.core.base.dict.ZeroOne;
import com.dlshouwen.swda.core.base.exception.SwdaException;

/**
 * schedule utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ScheduleUtils {

	/** job name */
	private final static String JOB_NAME = "JOB_NAME_";

	/** job param key */
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

	/**
	 * get job class
	 * @params scheduleJob
	 * @return job class
	 */
	public static Class<? extends Job> getJobClass(ScheduleJob scheduleJob) {
//		disallow concurrent
		if (scheduleJob.getAllowConcurrent().equals(ZeroOne.NO)) {
//			return disallow concurrent execution
			return ScheduleDisallowConcurrentExecution.class;
		} else {
//			return allow concurrent execution
			return ScheduleConcurrentExecution.class;
		}
	}

	/**
	 * get trigger key
	 * @params scheduleJob
	 * @return trigger key
	 */
	public static TriggerKey getTriggerKey(ScheduleJob scheduleJob) {
//		get trigger key
		return TriggerKey.triggerKey(JOB_NAME + scheduleJob.getScheduleJobId(), scheduleJob.getScheduleJobGroup());
	}

	/**
	 * get job key
	 * @params scheduleJob
	 * @return job key
	 */
	public static JobKey getJobKey(ScheduleJob scheduleJob) {
//		get job key
		return JobKey.jobKey(JOB_NAME + scheduleJob.getScheduleJobId(), scheduleJob.getScheduleJobGroup());
	}

	/**
	 * create schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		try catch
		try {
//			get job key
			JobKey jobKey = getJobKey(scheduleJob);
//			get job detail
			JobDetail jobDetail = JobBuilder.newJob(getJobClass(scheduleJob)).withIdentity(jobKey).build();
//			create schedule builder
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
//			get trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob)).withSchedule(scheduleBuilder).build();
//			set schedule job to params
			jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
//			set schedule job
			scheduler.scheduleJob(jobDetail, trigger);
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				delete job
				scheduler.deleteJob(jobKey);
			}
//			if has next execution date
			if (CronUtils.getNextExecution(scheduleJob.getCronExpression()) != null) {
//				execute schedule job
				scheduler.scheduleJob(jobDetail, trigger);
			}
//			if schedule job status is pause
			if (scheduleJob.getScheduleJobStatus().equals(ScheduleJobStatus.PAUSE)) {
//				pause job
				scheduler.pauseJob(jobKey);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("create schedule job failure", e);
		}
	}

	/**
	 * run schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void runScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		try catch
		try {
//			create data map
			JobDataMap dataMap = new JobDataMap();
//			set schedule job to param
			dataMap.put(JOB_PARAM_KEY, scheduleJob);
//			get job key
			JobKey jobKey = getJobKey(scheduleJob);
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				trigger job
				scheduler.triggerJob(jobKey, dataMap);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("schedule run failure", e);
		}
	}

	/**
	 * pause schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void pauseScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		try catch
		try {
//			pause schedule job
			scheduler.pauseJob(getJobKey(scheduleJob));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("pause schedule job failure", e);
		}
	}

	/**
	 * resule schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void resumeScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		try catch
		try {
//			resume schedule job
			scheduler.resumeJob(getJobKey(scheduleJob));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("resume schedule job failure", e);
		}
	}

	/**
	 * update schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		get job key
		JobKey jobKey = ScheduleUtils.getJobKey(scheduleJob);
//		try catch
		try {
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				delete schedule job
				scheduler.deleteJob(jobKey);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("update schedule job failure", e);
		}
//		create schedule job
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	/**
	 * delete schedule job
	 * @params scheduler
	 * @params schedule job
	 */
	public static void deleteScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
//		try catch
		try {
//			delete schedule job
			scheduler.deleteJob(getJobKey(scheduleJob));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("delete schedule job failure", e);
		}
	}

}