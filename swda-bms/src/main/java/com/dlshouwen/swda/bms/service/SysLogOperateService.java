package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.OperationLog;
import com.dlshouwen.swda.bms.query.SysLogOperateQuery;
import com.dlshouwen.swda.bms.vo.OperationLogVO;

/**
 * operation log service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysLogOperateService extends BaseService<OperationLog> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<OperationLogVO> page(SysLogOperateQuery query);

}