package com.dlshouwen.swda.bms.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.log.convert.ScheduleJobLogConvert;
import com.dlshouwen.swda.bms.log.entity.ScheduleJobLog;
import com.dlshouwen.swda.bms.log.mapper.ScheduleJobLogMapper;
import com.dlshouwen.swda.bms.log.service.IScheduleJobLogService;
import com.dlshouwen.swda.bms.log.vo.ScheduleJobLogVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * schedule job log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements IScheduleJobLogService {
	
	/**
	 * get schedule job log page result
	 * @param query
	 * @return schedule job log page result
	 */
	@Override
	public PageResult<ScheduleJobLogVO> getScheduleJobLogPageResult(Query<ScheduleJobLog> query) {
//		query page
		IPage<ScheduleJobLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(ScheduleJobLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete schedule job log
	 * @param scheduleJobLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteScheduleJobLog(List<Long> scheduleJobLogIdList) {
		this.removeByIds(scheduleJobLogIdList);
	}

}