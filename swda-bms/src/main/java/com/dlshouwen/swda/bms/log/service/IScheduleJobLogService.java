package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.entity.ScheduleJobLog;
import com.dlshouwen.swda.bms.log.vo.ScheduleJobLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * schedule job log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IScheduleJobLogService extends BaseService<ScheduleJobLog> {

	/**
	 * get schedule job log page result
	 * @param query
	 * @return schedule job log page result
	 */
	PageResult<ScheduleJobLogVO> getScheduleJobLogPageResult(Query<ScheduleJobLog> query);

	/**
	 * delete schedule job log
	 * @param scheduleJobLogIdList
	 */
	void deleteScheduleJobLog(List<Long> scheduleJobLogIdList);

}