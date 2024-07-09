package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.*;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.entity.log.LoginLog;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.LoginLogConvert;
import com.dlshouwen.swda.bms.mapper.LoginLogMapper;
import com.dlshouwen.swda.bms.service.ILoginLogService;
import com.dlshouwen.swda.bms.vo.LoginLogVO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * login log service impl
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

	/**
	 * get login log list
	 * 
	 * @param query
	 * @return login log list
	 */
	@Override
	public PageResult<LoginLogVO> getLoginLogList(Query<LoginLog> query) {
//		query page
		IPage<LoginLog> page = GridUtils.query(baseMapper, query);
//		convert to vo for return 
		return new PageResult<>(LoginLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * save
	 * 
	 * @param username
	 * @param status
	 * @param operation
	 */
	@Override
	public void saveLoginLog(String username, Integer status, Integer operation) {
//		TODO save
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		assert
		assert request != null;
//		get user agent, ip
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		String ip = IpUtils.getIp(request);
//		defined login log
		LoginLog loginLog = new LoginLog();
//		set user name, status, operation, ip, address, user agent
//		loginLog.setUsername(username);
//		loginLog.setStatus(status);
//		loginLog.setOperation(operation);
		loginLog.setIp(ip);
//		loginLog.setAddress(address);
		loginLog.setUserAgent(userAgent);
//		insert
		baseMapper.insert(loginLog);
	}

}