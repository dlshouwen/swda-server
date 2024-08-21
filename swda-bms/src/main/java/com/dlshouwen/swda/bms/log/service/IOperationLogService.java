package com.dlshouwen.swda.bms.log.service;

import com.dlshouwen.swda.bms.log.vo.OperationLogVO;
import com.dlshouwen.swda.core.grid.entity.PageResult;
import com.dlshouwen.swda.core.grid.entity.Query;
import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

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