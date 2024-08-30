package com.dlshouwen.swda.bms.log.service;

import com.dlshouwen.swda.bms.log.vo.LoginLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * login log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
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
	 * @param loginType
	 * @param loginStatus
	 * @param loginInfo
	 * @param loginMessage
	 * @return log id
	 */
	Long saveLoginLog(Integer loginType, Integer loginStatus, String loginInfo, String loginMessage);

}