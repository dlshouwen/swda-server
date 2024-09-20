package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.vo.OperationLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * operation log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IOperationLogService extends BaseService<OperationLog> {

	/**
	 * get operation log page result
	 * @param query
	 * @return operation log page result
	 */
	PageResult<OperationLogVO> getOperationLogPageResult(Query<OperationLog> query);

	/**
	 * delete operation log
	 * @param operationLogIdList
	 */
	void deleteOperationLog(List<Long> operationLogIdList);

}