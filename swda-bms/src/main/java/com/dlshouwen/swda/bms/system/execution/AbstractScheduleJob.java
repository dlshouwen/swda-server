package com.dlshouwen.swda.bms.system.execution;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import com.dlshouwen.swda.bms.log.entity.JobLog;
import com.dlshouwen.swda.bms.log.service.IJobLogService;
import com.dlshouwen.swda.bms.system.entity.Job;
import com.dlshouwen.swda.bms.system.utils.ScheduleUtils;
import com.dlshouwen.swda.core.log.dict.CallResult;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * abstract schedule job
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
public abstract class AbstractScheduleJob implements org.quartz.Job {

	/** thread local */
	private static final ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

	/**
	 * execute
	 * @param context
	 */
	@Override
	public void execute(JobExecutionContext context) {
//		create job
		Job job = new Job();
//		copy job
		BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY), job);
//		try catch
		try {
//			set start time
			threadLocal.set(LocalDateTime.now());
//			log
			log.info("run job start, job id: {}", job.getJobId());
//			get bean
			Object bean = SpringUtil.getBean(job.getBeanName());
//			get method
			Method method = bean.getClass().getDeclaredMethod(job.getMethodName(), String.class);
//			invoke
			method.invoke(bean, job.getParams());
//			log
			log.info("run job completed, job id: {}", job.getJobId());
//			save job log
			saveJobLog(job, null);
		} catch (Exception e) {
//			log error
			log.error("run job failure, job id: {}", job.getJobId(), e);
//			save job log
			saveJobLog(job, e);
		}
	}

	/**
	 * save job log
	 * @params job
	 * @params exception
	 */
	protected void saveJobLog(Job job, Exception exception) {
//		get start time
		LocalDateTime start = threadLocal.get();
//		remove thread local
		threadLocal.remove();
//		get end time
		LocalDateTime end = LocalDateTime.now();
//		get cost
		long cost = ChronoUnit.MILLIS.between(start, end);
//		create job log
		JobLog jobLog = new JobLog();
		jobLog.setJobId(job.getJobId());
		jobLog.setJobCode(job.getJobCode());
		jobLog.setJobName(job.getJobName());
		jobLog.setJobGroup(job.getJobGroup());
		jobLog.setBeanName(job.getBeanName());
		jobLog.setMethodName(job.getMethodName());
		jobLog.setParams(job.getParams());
		jobLog.setCallResult(CallResult.SUCCESS);
		jobLog.setStartTime(start);
		jobLog.setEndTime(end);
		jobLog.setCost(cost);
//		has exception
		if (exception != null) {
//			set call result
			jobLog.setCallResult(CallResult.FAILURE);
//			set error reason
			jobLog.setErrorReason(ExceptionUtil.stacktraceToString(exception));
		}
//		save job log
		SpringUtil.getBean(IJobLogService.class).save(jobLog);
	}

}
