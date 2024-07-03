package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fhs.trans.service.impl.TransService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.dlshouwen.swda.core.utils.*;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.LoginLogConvert;
import com.dlshouwen.swda.bms.mapper.LoginLogMapper;
import com.dlshouwen.swda.bms.entity.LoginLog;
import com.dlshouwen.swda.bms.query.SysLogLoginQuery;
import com.dlshouwen.swda.bms.service.ILoginLogService;
import com.dlshouwen.swda.bms.vo.LoginLogVO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * login log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
	
	/** trans service */
	private final TransService transService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<LoginLogVO> page(SysLogLoginQuery query) {
//		select page
		IPage<LoginLog> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(LoginLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private LambdaQueryWrapper<LoginLog> getWrapper(SysLogLoginQuery query) {
//		create wrapper
		LambdaQueryWrapper<LoginLog> wrapper = Wrappers.lambdaQuery();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getUsername()), LoginLog::getUsername, query.getUsername());
		wrapper.like(StrUtil.isNotBlank(query.getAddress()), LoginLog::getAddress, query.getAddress());
		wrapper.like(query.getStatus() != null, LoginLog::getStatus, query.getStatus());
		wrapper.orderByDesc(LoginLog::getId);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param username
	 * @param status
	 * @param operation
	 */
	@Override
	public void save(String username, Integer status, Integer operation) {
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		assert
		assert request != null;
//		get user agent, ip, address
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		String ip = IpUtils.getIp(request);
		String address = IpUtils.getAddressByIP(ip);
//		defined login log
		LoginLog entity = new LoginLog();
//		set user name, status, operation, ip, address, user agent
		entity.setUsername(username);
		entity.setStatus(status);
		entity.setOperation(operation);
		entity.setIp(ip);
		entity.setAddress(address);
		entity.setUserAgent(userAgent);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * export
	 */
	@Override
	@SneakyThrows
	public void export() {
//		get login log list
		List<LoginLog> list = list();
//		convert to login log vo
		List<LoginLogVO> sysLogLoginVOS = LoginLogConvert.INSTANCE.convertList(list);
//		batch trans
		transService.transBatch(sysLogLoginVOS);
//		export excel
		ExcelUtils.excelExport(LoginLogVO.class, "system_login_log_excel" + DateUtils.format(new Date()), null, sysLogLoginVOS);
	}

}