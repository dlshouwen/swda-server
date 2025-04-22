package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.entity.System;
import com.dlshouwen.swda.bms.system.vo.SystemVO;

import java.util.List;

/**
 * system service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ISystemService extends BaseService<System> {

	/**
	 * get login user system list
	 * @param user
	 * @return login user system list
	 */
	List<SystemVO> getLoginUserSystemList(UserDetail user);

	/**
	 * get system page result
	 * @param query
	 * @return system page result
	 */
	PageResult<SystemVO> getSystemPageResult(Query<System> query);
	
	/**
	 * get system list
	 * @return system vo list
	 */
	List<SystemVO> getSystemList();

	/**
	 * get system data
	 * @param systemId
	 * @return system data
	 */
	SystemVO getSystemData(Long systemId);

	/**
	 * add system
	 * @param systemVO
	 */
	void addSystem(SystemVO vo);

	/**
	 * update system
	 * @param systemVO
	 */
	void updateSystem(SystemVO vo);

	/**
	 * delete system
	 * @param systemIdList
	 */
	void deleteSystem(List<Long> systemIdList);

}
