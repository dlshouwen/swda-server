package com.dlshouwen.swda.bms.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.common.utils.HttpContextUtils;
import com.dlshouwen.swda.core.common.utils.IpUtils;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.log.convert.LoginLogConvert;
import com.dlshouwen.swda.bms.log.mapper.LoginLogMapper;
import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.log.vo.LoginLogVO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * login log service impl
 * 
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

	/**
	 * get login log page result
	 * @param query
	 * @return login log page result
	 */
	@Override
	public PageResult<LoginLogVO> getLoginLogPageResult(Query<LoginLog> query) {
//		query page
		IPage<LoginLog> page = this.page(query);
//		convert to vo for return 
		return new PageResult<>(LoginLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete login log
	 * @param loginLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteLoginLog(List<Long> loginLogIdList) {
		this.removeByIds(loginLogIdList);
	}

	/**
	 * save login log
	 * @param loginType
	 * @param loginStatus
	 * @param loginInfo
	 * @param loginMessage
	 * @param operation
	 * @return login log id
	 */
	@Override
	public Long saveLoginLog(Integer loginType, Integer loginStatus, String loginInfo, String loginMessage) {
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		assert
		assert request != null;
//		defined login log
		LoginLog loginLog = new LoginLog();
//		get user agent, ip
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		String ip = IpUtils.getIp(request);
//		set user agent, ip
		loginLog.setUserAgent(userAgent);
		loginLog.setIp(ip);
//		set login type, login status, login time
		loginLog.setLoginType(loginType);
		loginLog.setLoginStatus(loginStatus);
		loginLog.setLoginTime(LocalDateTime.now());
//		set login info, login message
		loginLog.setLoginInfo(loginInfo);
		loginLog.setLoginMessage(loginMessage);
//		get user
		UserDetail user = SecurityUser.getUser();
//		set tenent id, user id, real name, organ id, organ name
		if(user!=null) {
			loginLog.setTenantId(user.getTenantId());
			loginLog.setUserId(user.getUserId());
			loginLog.setRealName(user.getRealName());
			loginLog.setOrganId(user.getOrganId());
			loginLog.setOrganName(user.getOrganName());
		}
//		set is logout
		loginLog.setIsLogout(ZeroOne.NO);
//		insert
		this.save(loginLog);
//		return login log id
		return loginLog.getLoginLogId();
	}
	
	/**
	 * save logout log
	 * @param logoutType
	 */
	@Override
	public void saveLogoutLog(Integer logoutType) {
//		get user
		UserDetail user = SecurityUser.getUser();
//		if has user
		if(user!=null) {
//			get login log
			LoginLog loginLog = this.getById(user.getLoginLogId());
//			set is logout, logout type, logout time
			loginLog.setIsLogout(ZeroOne.YES);
			loginLog.setLogoutType(logoutType);
			loginLog.setLogoutTime(LocalDateTime.now());
//			save login log
			this.save(loginLog);
		}
	}

}