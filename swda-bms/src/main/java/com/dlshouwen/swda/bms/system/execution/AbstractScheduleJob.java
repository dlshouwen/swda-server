package com.dlshouwen.swda.bms.system.execution;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import com.dlshouwen.swda.bms.log.entity.ScheduleJobLog;
import com.dlshouwen.swda.bms.log.service.IScheduleJobLogService;
import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
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
public abstract class AbstractScheduleJob implements Job {

	/** thread local */
	private static final ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

	/**
	 * execute
	 * @param context
	 */
	@Override
	public void execute(JobExecutionContext context) {
//		create schedule job
		ScheduleJob scheduleJob = new ScheduleJob();
//		copy schedule job
		BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY), scheduleJob);
//		try catch
		try {
//			set start time
			threadLocal.set(LocalDateTime.now());
//			log
			log.info("execute schedule job start, schedule job id: {}", scheduleJob.getScheduleJobId());
//			get bean
			Object bean = SpringUtil.getBean(scheduleJob.getBeanName());
//			get method
			Method method = bean.getClass().getDeclaredMethod(scheduleJob.getMethodName(), String.class);
//			invoke
			method.invoke(bean, scheduleJob.getParams());
//			log
			log.info("execute schedule job completed, schedule job id: {}", scheduleJob.getScheduleJobId());
//			save schedule job log
			saveScheduleJobLog(scheduleJob, null);
		} catch (Exception e) {
//			log error
			log.error("schedule job execute failure, schedule job id: {}", scheduleJob.getScheduleJobId(), e);
//			save schedule job log
			saveScheduleJobLog(scheduleJob, e);
		}
	}

	/**
	 * save schedule job log
	 * @params scheduleJob
	 * @params exception
	 */
	protected void saveScheduleJobLog(ScheduleJob scheduleJob, Exception exception) {
//		get start time
		LocalDateTime start = threadLocal.get();
//		remove thread local
		threadLocal.remove();
//		get end time
		LocalDateTime end = LocalDateTime.now();
//		get cost
		long cost = ChronoUnit.MILLIS.between(start, end);
//		create schedule job log
		ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
		scheduleJobLog.setScheduleJobId(scheduleJob.getScheduleJobId());
		scheduleJobLog.setScheduleJobCode(scheduleJob.getScheduleJobCode());
		scheduleJobLog.setScheduleJobName(scheduleJob.getScheduleJobName());
		scheduleJobLog.setScheduleJobGroup(scheduleJob.getScheduleJobGroup());
		scheduleJobLog.setBeanName(scheduleJob.getBeanName());
		scheduleJobLog.setMethodName(scheduleJob.getMethodName());
		scheduleJobLog.setParams(scheduleJob.getParams());
		scheduleJobLog.setCallResult(CallResult.SUCCESS);
		scheduleJobLog.setStartTime(start);
		scheduleJobLog.setEndTime(end);
		scheduleJobLog.setCost(cost);
//		has exception
		if (exception != null) {
//			set call result
			scheduleJobLog.setCallResult(CallResult.FAILURE);
//			set error reason
			scheduleJobLog.setErrorReason(ExceptionUtil.stacktraceToString(exception));
		}
//		save schedule job log
		SpringUtil.getBean(IScheduleJobLogService.class).save(scheduleJobLog);
	}

}
