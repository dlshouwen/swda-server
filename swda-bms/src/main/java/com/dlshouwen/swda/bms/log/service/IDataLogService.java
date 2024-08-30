package com.dlshouwen.swda.bms.log.service;

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
	 * get data log list
	 * @param query
	 * @return page result
	 */
	PageResult<DataLogVO> getDataLogList(Query<DataLog> query);

}