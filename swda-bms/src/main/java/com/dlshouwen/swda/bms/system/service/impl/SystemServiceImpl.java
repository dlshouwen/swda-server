package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.convert.SystemConvert;
import com.dlshouwen.swda.bms.system.entity.System;
import com.dlshouwen.swda.bms.system.mapper.SystemMapper;
import com.dlshouwen.swda.bms.system.service.ISystemService;
import com.dlshouwen.swda.bms.system.vo.SystemVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * system service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class SystemServiceImpl extends BaseServiceImpl<SystemMapper, System> implements ISystemService {

	/**
	 * get login user system list
	 * @param user
	 * @return login user system list
	 */
	@Override
	public List<SystemVO> getLoginUserSystemList(UserDetail user) {
//		defined system list
		List<System> systemList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get system list
			systemList = baseMapper.selectList(null);
		} else {
//			get login user system list
			systemList = baseMapper.getLoginUserSystemList(user.getUserId());
		}
//		convert to system vo for return
		return SystemConvert.INSTANCE.convert2VOList(systemList);
	}
	
	/**
	 * get system page result
	 * @param query
	 * @return system page result
	 */
	@Override
	public PageResult<SystemVO> getSystemPageResult(Query<System> query) {
//		query page
		IPage<System> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(SystemConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get system data
	 * @param systemId
	 * @return system data
	 */
	@Override
	public SystemVO getSystemData(Long systemId) {
//		get system data
		System system = this.getById(systemId);
//		convert to system vo for return
		return SystemConvert.INSTANCE.convert2VO(system);
	}

	/**
	 * add system
	 * @param systemVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addSystem(SystemVO systemVO) {
//		convert to system
		System system = SystemConvert.INSTANCE.convert(systemVO);
//		insert system
		this.save(system);
	}

	/**
	 * update system
	 * @param systemVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateSystem(SystemVO systemVO) {
//		convert to system
		System system = SystemConvert.INSTANCE.convert(systemVO);
//		update system
		this.updateById(system);
	}

	/**
	 * delete system
	 * @param systemIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSystem(List<Long> systemIdList) {
//		dlete system list
		this.removeByIds(systemIdList);
	}

}