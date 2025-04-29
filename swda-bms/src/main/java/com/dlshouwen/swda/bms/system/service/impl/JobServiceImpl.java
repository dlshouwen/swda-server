package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import jakarta.annotation.PostConstruct;

import com.dlshouwen.swda.bms.system.convert.JobConvert;
import com.dlshouwen.swda.bms.system.dict.JobStatus;
import com.dlshouwen.swda.bms.system.entity.Job;
import com.dlshouwen.swda.bms.system.mapper.JobMapper;
import com.dlshouwen.swda.bms.system.service.IJobService;
import com.dlshouwen.swda.bms.system.utils.ScheduleUtils;
import com.dlshouwen.swda.bms.system.vo.JobVO;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * job service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class JobServiceImpl extends BaseServiceImpl<JobMapper, Job> implements IJobService {
	
	/** scheduler */
	private final Scheduler scheduler;

	/**
	 * init
	 * @throws SchedulerException
	 */
	@PostConstruct
	public void init() throws SchedulerException {
//		clear all scheduler
		scheduler.clear();
//		get all job list
		List<Job> jobList = baseMapper.selectList(null);
//		for each job
		for (Job job : jobList) {
//			create job
			ScheduleUtils.createJob(scheduler, job);
		}
	}

	/**
	 * get job page result
	 * @param query
	 * @return job page result
	 */
	@Override
	public PageResult<JobVO> getJobPageResult(Query<Job> query) {
//		query page
		IPage<Job> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(JobConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get job data
	 * @param jobId
	 * @return job data
	 */
	@Override
	public JobVO getJobData(Long jobId) {
//		get job data
		Job job = this.getById(jobId);
//		convert to job vo for return
		return JobConvert.INSTANCE.convert2VO(job);
	}

	/**
	 * add job
	 * @param jobVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addJob(JobVO jobVO) {
//		convert to job
		Job job = JobConvert.INSTANCE.convert(jobVO);
//		status: normal
		if(job.getJobStatus().equals(JobStatus.NORMAL)) {
//			create job
			ScheduleUtils.createJob(scheduler, job);
		}
//		insert job
		this.save(job);
	}

	/**
	 * update job
	 * @param jobVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJob(JobVO jobVO) {
//		convert to job
		Job job = JobConvert.INSTANCE.convert(jobVO);
//		update job
		boolean result = this.updateById(job);
//		update success
		if(result) {
//			get job
			Job _job = this.getById(job.getJobId());
//			update job
            ScheduleUtils.updateJob(scheduler, _job);
		}
	}

	/**
	 * delete job
	 * @param jobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJob(List<Long> jobIdList) {
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			remove job
			this.removeById(jobId);
//			delete job
			ScheduleUtils.deleteJob(scheduler, job);
		}
	}
	
	/**
	 * pause job
	 * @param jobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pauseJob(List<Long> jobIdList) {
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			if pause
			if(JobStatus.PAUSE.equals(job.getJobStatus())) {
//				throw exception
				throw new SwdaException("编号为 ["+jobId+"] 的任务当前为暂停状态，请确认。");
			}
		}
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			set pause
			job.setJobStatus(JobStatus.PAUSE);
//			update job
			this.updateById(job);
//			pause job
			ScheduleUtils.pauseJob(scheduler, job);
		}
	}
	
	/**
	 * resume job
	 * @param jobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resumeJob(List<Long> jobIdList) {
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			if normal
			if(JobStatus.NORMAL.equals(job.getJobStatus())) {
//				throw exception
				throw new SwdaException("编号为 ["+jobId+"] 的任务当前为正常状态，请确认。");
			}
		}
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			set normal
			job.setJobStatus(JobStatus.NORMAL);
//			update job
			this.updateById(job);
//			resume job
			ScheduleUtils.resumeJob(scheduler, job);
		}
	}
	
	/**
	 * run job
	 * @param jobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void runJob(List<Long> jobIdList) {
//		foe each job id
		for(Long jobId : jobIdList) {
//			get job
			Job job = this.getById(jobId);
//			if has job
			if(job != null) {
//				run job
				ScheduleUtils.runJob(scheduler, job);
			}
		}
	}

}