package com.dlshouwen.swda.bms.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlshouwen.swda.bms.log.convert.SmsLogConvert;
import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.mapper.SmsLogMapper;
import com.dlshouwen.swda.bms.log.service.ISmsLogService;
import com.dlshouwen.swda.bms.log.vo.SmsLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * sms log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLogMapper, SmsLog> implements ISmsLogService {

	/**
	 * get sms log page result
	 * @param query
	 * @return sms log page result
	 */
	@Override
	public PageResult<SmsLogVO> getSmsLogPageResult(Query<SmsLog> query) {
//		query page
		IPage<SmsLog> page = this.page(query);
//		convert to vo list for return 
		return new PageResult<>(SmsLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete sms log
	 * @param smsLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSmsLog(List<Long> smsLogIdList) {
		this.removeByIds(smsLogIdList);
	}

}