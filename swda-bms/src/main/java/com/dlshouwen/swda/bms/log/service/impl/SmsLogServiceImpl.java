package com.dlshouwen.swda.bms.log.service.impl;

import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.mapper.SmsLogMapper;
import com.dlshouwen.swda.bms.log.service.ISmsLogService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * sms log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLogMapper, SmsLog> implements ISmsLogService {

}