package com.dlshouwen.swda.bms.log.service;

import com.dlshouwen.swda.bms.log.vo.LoginLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

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