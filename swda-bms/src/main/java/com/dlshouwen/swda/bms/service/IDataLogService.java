package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.entity.log.DataLog;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.vo.DataLogVO;

/**
 * data log service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IDataLogService extends BaseService<DataLog> {

	/**
	 * get data log list
	 * @param query
	 * @return page result
	 */
	PageResult<DataLogVO> getDataLogList(Query<DataLog> query);

}