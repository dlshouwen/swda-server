package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.ScheduleJobConvert;
import com.dlshouwen.swda.bms.system.dict.ScheduleJobStatus;
import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
import com.dlshouwen.swda.bms.system.mapper.ScheduleJobMapper;
import com.dlshouwen.swda.bms.system.service.IScheduleJobService;
import com.dlshouwen.swda.bms.system.utils.ScheduleUtils;
import com.dlshouwen.swda.bms.system.vo.ScheduleJobVO;

import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * schedule job service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobMapper, ScheduleJob> implements IScheduleJobService {
	
	/** scheduler */
	private final Scheduler scheduler;

	/**
	 * get schedule job page result
	 * @param query
	 * @return schedule job page result
	 */
	@Override
	public PageResult<ScheduleJobVO> getScheduleJobPageResult(Query<ScheduleJob> query) {
//		query page
		IPage<ScheduleJob> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(ScheduleJobConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get schedule job data
	 * @param scheduleJobId
	 * @return schedule job data
	 */
	@Override
	public ScheduleJobVO getScheduleJobData(Long scheduleJobId) {
//		get schedule job data
		ScheduleJob scheduleJob = this.getById(scheduleJobId);
//		convert to schedule job vo for return
		return ScheduleJobConvert.INSTANCE.convert2VO(scheduleJob);
	}

	/**
	 * add schedule job
	 * @param scheduleJobVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addScheduleJob(ScheduleJobVO scheduleJobVO) {
//		convert to schedule job
		ScheduleJob scheduleJob = ScheduleJobConvert.INSTANCE.convert(scheduleJobVO);
//		status: normal
		if(scheduleJob.getScheduleJobStatus().equals(ScheduleJobStatus.NORMAL)) {
//			create schedule job
			ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
		}
//		insert schedule job
		this.save(scheduleJob);
	}

	/**
	 * update schedule job
	 * @param scheduleJobVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateScheduleJob(ScheduleJobVO scheduleJobVO) {
//		convert to schedule job
		ScheduleJob scheduleJob = ScheduleJobConvert.INSTANCE.convert(scheduleJobVO);
//		update schedule job
		boolean result = this.updateById(scheduleJob);
//		update success
		if(result) {
//			get schedule job
			ScheduleJob _scheduleJob = this.getById(scheduleJob.getScheduleJobId());
//			update schedule job
            ScheduleUtils.updateScheduleJob(scheduler, _scheduleJob);
		}
	}

	/**
	 * delete schedule job
	 * @param scheduleJobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteScheduleJob(List<Long> scheduleJobIdList) {
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			remove schedule job
			this.removeById(scheduleJobId);
//			delete schedule job
			ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob);
		}
	}
	
	/**
	 * pause schedule job
	 * @param scheduleJobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pauseScheduleJob(List<Long> scheduleJobIdList) {
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			if pause
			if(ScheduleJobStatus.PAUSE.equals(scheduleJob.getScheduleJobStatus())) {
//				throw exception
				throw new SwdaException("编号为 ["+scheduleJobId+"] 的任务当前为暂停状态，请确认。");
			}
		}
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			set pause
			scheduleJob.setScheduleJobStatus(ScheduleJobStatus.PAUSE);
//			update schedule job
			this.updateById(scheduleJob);
//			pause schedule job
			ScheduleUtils.pauseScheduleJob(scheduler, scheduleJob);
		}
	}
	
	/**
	 * resume schedule job
	 * @param scheduleJobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resumeScheduleJob(List<Long> scheduleJobIdList) {
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			if normal
			if(ScheduleJobStatus.NORMAL.equals(scheduleJob.getScheduleJobStatus())) {
//				throw exception
				throw new SwdaException("编号为 ["+scheduleJobId+"] 的任务当前为正常状态，请确认。");
			}
		}
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			set normal
			scheduleJob.setScheduleJobStatus(ScheduleJobStatus.NORMAL);
//			update schedule job
			this.updateById(scheduleJob);
//			resume schedule job
			ScheduleUtils.resumeScheduleJob(scheduler, scheduleJob);
		}
	}
	
	/**
	 * run schedule job
	 * @param scheduleJobIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void runScheduleJob(List<Long> scheduleJobIdList) {
//		foe each schedule job id
		for(Long scheduleJobId : scheduleJobIdList) {
//			get schedule job
			ScheduleJob scheduleJob = this.getById(scheduleJobId);
//			if has schedule job
			if(scheduleJob != null) {
//				run schedule job
				ScheduleUtils.runScheduleJob(scheduler, scheduleJob);
			}
		}
	}

}