package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.entity.EmailLog;
import com.dlshouwen.swda.bms.log.vo.EmailLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * email log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IEmailLogService extends BaseService<EmailLog> {

	/**
	 * get email log page result
	 * @param query
	 * @return email log page result
	 */
	PageResult<EmailLogVO> getEmailLogPageResult(Query<EmailLog> query);

	/**
	 * delete email log
	 * @param emailLogIdList
	 */
	void deleteEmailLog(List<Long> emailLogIdList);

}