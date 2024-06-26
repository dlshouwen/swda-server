package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.bms.query.SysLogLoginQuery;
import com.dlshouwen.swda.bms.vo.SysLogLoginVO;

/**
 * 登录日志
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

	/**
	 * Page result.
	 *
	 * @param query the query
	 * @return the page result
	 */
	PageResult<SysLogLoginVO> page(SysLogLoginQuery query);

	/**
	 * 保存登录日志
	 *
	 * @param username  用户名
	 * @param status    登录状态
	 * @param operation 操作信息
	 */
	void save(String username, Integer status, Integer operation);

	/**
	 * 导出登录日志
	 */
	void export();
}