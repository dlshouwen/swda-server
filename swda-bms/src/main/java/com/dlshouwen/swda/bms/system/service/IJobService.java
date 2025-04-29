package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.Job;
import com.dlshouwen.swda.bms.system.vo.JobVO;

import java.util.List;

/**
 * job service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IJobService extends BaseService<Job> {

	/**
	 * get job page result
	 * @param query
	 * @return job page result
	 */
	PageResult<JobVO> getJobPageResult(Query<Job> query);

	/**
	 * get job data
	 * @param jobId
	 * @return job data
	 */
	JobVO getJobData(Long jobId);

	/**
	 * add job
	 * @param jobVO
	 */
	void addJob(JobVO vo);

	/**
	 * update job
	 * @param jobVO
	 */
	void updateJob(JobVO vo);
	
	/**
	 * delete job
	 * @param jobIdList
	 */
	void deleteJob(List<Long> jobIdList);
	
	/**
	 * pause job
	 * @param jobIdList
	 */
	void pauseJob(List<Long> jobIdList);

	/**
	 * resume job
	 * @param jobIdList
	 */
	void resumeJob(List<Long> jobIdList);
	
	/**
	 * run job
	 * @param jobIdList
	 */
	void runJob(List<Long> jobIdList);

}
