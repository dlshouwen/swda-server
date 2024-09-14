package com.dlshouwen.swda.bms.log.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlshouwen.swda.bms.log.convert.EmailLogConvert;
import com.dlshouwen.swda.bms.log.entity.EmailLog;
import com.dlshouwen.swda.bms.log.mapper.EmailLogMapper;
import com.dlshouwen.swda.bms.log.service.IEmailLogService;
import com.dlshouwen.swda.bms.log.vo.EmailLogVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * email log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class EmailLogServiceImpl extends BaseServiceImpl<EmailLogMapper, EmailLog> implements IEmailLogService {

	/**
	 * get email log page result
	 * @param query
	 * @return email log page result
	 */
	@Override
	public PageResult<EmailLogVO> getEmailLogPageResult(Query<EmailLog> query) {
//		query page
		IPage<EmailLog> page = this.page(query);
//		convert to vo list for return 
		return new PageResult<>(EmailLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete email log
	 * @param emailLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteEmailLog(List<Long> emailLogIdList) {
		this.removeByIds(emailLogIdList);
	}

}