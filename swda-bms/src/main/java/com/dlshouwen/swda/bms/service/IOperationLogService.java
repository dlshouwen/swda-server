package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.OperationLog;
import com.dlshouwen.swda.bms.vo.OperationLogVO;

/**
 * operation log service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IOperationLogService extends BaseService<OperationLog> {

	/**
	 * get operation log list
	 * @param query
	 * @return page result
	 */
	PageResult<OperationLogVO> getOperationLogList(Query<OperationLog> query);

}