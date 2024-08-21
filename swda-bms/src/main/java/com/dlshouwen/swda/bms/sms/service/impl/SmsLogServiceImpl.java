package com.dlshouwen.swda.bms.sms.service.impl;

import com.dlshouwen.swda.bms.sms.entity.SmsLog;
import com.dlshouwen.swda.bms.sms.mapper.SmsLogMapper;
import com.dlshouwen.swda.bms.sms.service.SmsLogService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * sms log service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLogMapper, SmsLog> implements SmsLogService {

}