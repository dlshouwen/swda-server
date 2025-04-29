package com.dlshouwen.swda.bms.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.log.convert.JobLogConvert;
import com.dlshouwen.swda.bms.log.entity.JobLog;
import com.dlshouwen.swda.bms.log.mapper.JobLogMapper;
import com.dlshouwen.swda.bms.log.service.IJobLogService;
import com.dlshouwen.swda.bms.log.vo.JobLogVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * job log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class JobLogServiceImpl extends BaseServiceImpl<JobLogMapper, JobLog> implements IJobLogService {
	
	/**
	 * get job log page result
	 * @param query
	 * @return job log page result
	 */
	@Override
	public PageResult<JobLogVO> getJobLogPageResult(Query<JobLog> query) {
//		query page
		IPage<JobLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(JobLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete job log
	 * @param jobLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJobLog(List<Long> jobLogIdList) {
		this.removeByIds(jobLogIdList);
	}

}