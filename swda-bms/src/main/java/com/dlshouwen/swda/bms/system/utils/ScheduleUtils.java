package com.dlshouwen.swda.bms.system.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.dlshouwen.swda.bms.system.dict.JobStatus;
import com.dlshouwen.swda.bms.system.entity.Job;
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
	 * @params job
	 * @return job class
	 */
	public static Class<? extends org.quartz.Job> getJobClass(Job job) {
//		disallow concurrent
		if (job.getAllowConcurrent().equals(ZeroOne.NO)) {
//			return disallow concurrent execution
			return ScheduleDisallowConcurrentExecution.class;
		} else {
//			return allow concurrent execution
			return ScheduleConcurrentExecution.class;
		}
	}

	/**
	 * get trigger key
	 * @params job
	 * @return trigger key
	 */
	public static TriggerKey getTriggerKey(Job job) {
//		get trigger key
		return TriggerKey.triggerKey(JOB_NAME + job.getJobId(), job.getJobGroup());
	}

	/**
	 * get job key
	 * @params job
	 * @return job key
	 */
	public static JobKey getJobKey(Job job) {
//		get job key
		return JobKey.jobKey(JOB_NAME + job.getJobId(), job.getJobGroup());
	}

	/**
	 * create job
	 * @params scheduler
	 * @params job
	 */
	public static void createJob(Scheduler scheduler, Job job) {
//		try catch
		try {
//			get job key
			JobKey jobKey = getJobKey(job);
//			get job detail
			JobDetail jobDetail = JobBuilder.newJob(getJobClass(job)).withIdentity(jobKey).build();
//			create schedule builder
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression()).withMisfireHandlingInstructionDoNothing();
//			get trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job)).withSchedule(scheduleBuilder).build();
//			set schedule job to params
			jobDetail.getJobDataMap().put(JOB_PARAM_KEY, job);
//			set schedule job
			scheduler.scheduleJob(jobDetail, trigger);
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				delete job
				scheduler.deleteJob(jobKey);
			}
//			if has next execution date
			if (CronUtils.getNextExecution(job.getCronExpression()) != null) {
//				execute schedule job
				scheduler.scheduleJob(jobDetail, trigger);
			}
//			if schedule job status is pause
			if (job.getJobStatus().equals(JobStatus.PAUSE)) {
//				pause job
				scheduler.pauseJob(jobKey);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("create job failure", e);
		}
	}

	/**
	 * run job
	 * @params scheduler
	 * @params job
	 */
	public static void runJob(Scheduler scheduler, Job job) {
//		try catch
		try {
//			create data map
			JobDataMap dataMap = new JobDataMap();
//			set schedule job to param
			dataMap.put(JOB_PARAM_KEY, job);
//			get job key
			JobKey jobKey = getJobKey(job);
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				trigger job
				scheduler.triggerJob(jobKey, dataMap);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("run job failure", e);
		}
	}

	/**
	 * pause job
	 * @params scheduler
	 * @params job
	 */
	public static void pauseJob(Scheduler scheduler, Job job) {
//		try catch
		try {
//			pause job
			scheduler.pauseJob(getJobKey(job));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("pause job failure", e);
		}
	}

	/**
	 * resule job
	 * @params scheduler
	 * @params job
	 */
	public static void resumeJob(Scheduler scheduler, Job job) {
//		try catch
		try {
//			resume job
			scheduler.resumeJob(getJobKey(job));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("resume job failure", e);
		}
	}

	/**
	 * update job
	 * @params scheduler
	 * @params job
	 */
	public static void updateJob(Scheduler scheduler, Job job) {
//		get job key
		JobKey jobKey = ScheduleUtils.getJobKey(job);
//		try catch
		try {
//			if exists
			if (scheduler.checkExists(jobKey)) {
//				delete job
				scheduler.deleteJob(jobKey);
			}
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("update job failure", e);
		}
//		create job
		ScheduleUtils.createJob(scheduler, job);
	}

	/**
	 * delete job
	 * @params scheduler
	 * @params job
	 */
	public static void deleteJob(Scheduler scheduler, Job job) {
//		try catch
		try {
//			delete job
			scheduler.deleteJob(getJobKey(job));
		} catch (SchedulerException e) {
//			throw exception
			throw new SwdaException("delete job failure", e);
		}
	}

}