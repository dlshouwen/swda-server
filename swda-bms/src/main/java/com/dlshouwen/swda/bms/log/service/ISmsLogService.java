package com.dlshouwen.swda.bms.log.service;

import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.vo.SmsLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * sms log service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ISmsLogService extends BaseService<SmsLog> {

	/**
	 * get sms log page result
	 * @param query
	 * @return sms log page result
	 */
	PageResult<SmsLogVO> getSmsLogPageResult(Query<SmsLog> query);
	
}