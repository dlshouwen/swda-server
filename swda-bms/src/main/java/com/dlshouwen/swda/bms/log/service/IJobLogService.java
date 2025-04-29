package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.entity.JobLog;
import com.dlshouwen.swda.bms.log.vo.JobLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * job log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IJobLogService extends BaseService<JobLog> {

	/**
	 * get job log page result
	 * @param query
	 * @return job log page result
	 */
	PageResult<JobLogVO> getJobLogPageResult(Query<JobLog> query);

	/**
	 * delete job log
	 * @param jobLogIdList
	 */
	void deleteJobLog(List<Long> jobLogIdList);

}