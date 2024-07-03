package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.LoginLog;
import com.dlshouwen.swda.bms.vo.LoginLogVO;

/**
 * login log service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface ILoginLogService extends BaseService<LoginLog> {

	/**
	 * get login log list
	 * @param query
	 * @return login log list
	 */
	PageResult<LoginLogVO> getLoginLogList(Query<LoginLog> query);

	/**
	 * save login log
	 * @param username
	 * @param status
	 * @param operation
	 */
	void saveLoginLog(String username, Integer status, Integer operation);

}