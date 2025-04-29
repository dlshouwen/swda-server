package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
import com.dlshouwen.swda.bms.system.vo.ScheduleJobVO;

import java.util.List;

/**
 * schedule job service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IScheduleJobService extends BaseService<ScheduleJob> {

	/**
	 * get schedule job page result
	 * @param query
	 * @return schedule job page result
	 */
	PageResult<ScheduleJobVO> getScheduleJobPageResult(Query<ScheduleJob> query);

	/**
	 * get schedule job data
	 * @param scheduleJobId
	 * @return schedule job data
	 */
	ScheduleJobVO getScheduleJobData(Long scheduleJobId);

	/**
	 * add schedule job
	 * @param scheduleJobVO
	 */
	void addScheduleJob(ScheduleJobVO vo);

	/**
	 * update schedule job
	 * @param scheduleJobVO
	 */
	void updateScheduleJob(ScheduleJobVO vo);
	
	/**
	 * delete schedule job
	 * @param scheduleJobIdList
	 */
	void deleteScheduleJob(List<Long> scheduleJobIdList);
	
	/**
	 * pause schedule job
	 * @param scheduleJobIdList
	 */
	void pauseScheduleJob(List<Long> scheduleJobIdList);

	/**
	 * resume schedule job
	 * @param scheduleJobIdList
	 */
	void resumeScheduleJob(List<Long> scheduleJobIdList);
	
	/**
	 * run schedule job
	 * @param scheduleJobIdList
	 */
	void runScheduleJob(List<Long> scheduleJobIdList);

}
