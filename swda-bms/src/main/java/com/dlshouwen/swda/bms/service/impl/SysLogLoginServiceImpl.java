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
import com.dlshouwen.swda.bms.convert.SysLogLoginConvert;
import com.dlshouwen.swda.bms.mapper.SysLogLoginDao;
import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.bms.query.SysLogLoginQuery;
import com.dlshouwen.swda.bms.service.SysLogLoginService;
import com.dlshouwen.swda.bms.vo.SysLogLoginVO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 登录日志
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysLogLoginServiceImpl extends BaseServiceImpl<SysLogLoginDao, SysLogLoginEntity>
		implements SysLogLoginService {
	private final TransService transService;

	@Override
	public PageResult<SysLogLoginVO> page(SysLogLoginQuery query) {
		IPage<SysLogLoginEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

		return new PageResult<>(SysLogLoginConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	private LambdaQueryWrapper<SysLogLoginEntity> getWrapper(SysLogLoginQuery query) {
		LambdaQueryWrapper<SysLogLoginEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(StrUtil.isNotBlank(query.getUsername()), SysLogLoginEntity::getUsername, query.getUsername());
		wrapper.like(StrUtil.isNotBlank(query.getAddress()), SysLogLoginEntity::getAddress, query.getAddress());
		wrapper.like(query.getStatus() != null, SysLogLoginEntity::getStatus, query.getStatus());
		wrapper.orderByDesc(SysLogLoginEntity::getId);

		return wrapper;
	}

	@Override
	public void save(String username, Integer status, Integer operation) {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		assert request != null;
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		String ip = IpUtils.getIp(request);
		String address = IpUtils.getAddressByIP(ip);

		SysLogLoginEntity entity = new SysLogLoginEntity();
		entity.setUsername(username);
		entity.setStatus(status);
		entity.setOperation(operation);
		entity.setIp(ip);
		entity.setAddress(address);
		entity.setUserAgent(userAgent);

		baseMapper.insert(entity);
	}

	@Override
	@SneakyThrows
	public void export() {
		List<SysLogLoginEntity> list = list();
		List<SysLogLoginVO> sysLogLoginVOS = SysLogLoginConvert.INSTANCE.convertList(list);
		transService.transBatch(sysLogLoginVOS);
		// 写到浏览器打开
		ExcelUtils.excelExport(SysLogLoginVO.class, "system_login_log_excel" + DateUtils.format(new Date()), null,
				sysLogLoginVOS);
	}

}