package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.vo.DataLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * data log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IDataLogService extends BaseService<DataLog> {

	/**
	 * get data log page result
	 * @param query
	 * @return data log page result
	 */
	PageResult<DataLogVO> getDataLogPageResult(Query<DataLog> query);

	/**
	 * delete data log
	 * @param dataLogIdList
	 */
	void deleteDataLog(List<Long> dataLogIdList);

}