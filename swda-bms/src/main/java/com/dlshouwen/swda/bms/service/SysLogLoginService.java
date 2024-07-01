package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.bms.query.SysLogLoginQuery;
import com.dlshouwen.swda.bms.vo.LoginLogVO;

/**
 * login log service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<LoginLogVO> page(SysLogLoginQuery query);

	/**
	 * save
	 * @param username
	 * @param status
	 * @param operation
	 */
	void save(String username, Integer status, Integer operation);

	/**
	 * export
	 */
	void export();

}