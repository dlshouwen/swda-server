package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.vo.LoginLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * login log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ILoginLogService extends BaseService<LoginLog> {

	/**
	 * get login log page result
	 * @param query
	 * @return login log page result
	 */
	PageResult<LoginLogVO> getLoginLogPageResult(Query<LoginLog> query);

	/**
	 * delete login log
	 * @param loginLogIdList
	 */
	void deleteLoginLog(List<Long> loginLogIdList);

	/**
	 * save login log
	 * @param loginType
	 * @param loginStatus
	 * @param loginInfo
	 * @param loginMessage
	 * @param user
	 * @return log id
	 */
	Long saveLoginLog(String loginType, String loginStatus, String loginInfo, String loginMessage, UserDetail user);
	
	/**
	 * save logout log
	 * @param logoutType
	 */
	void saveLogoutLog(String logoutType);

}