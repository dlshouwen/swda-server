package com.dlshouwen.swda.sms.service.impl;

import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.sms.entity.SmsLog;
import com.dlshouwen.swda.sms.mapper.SmsLogMapper;
import com.dlshouwen.swda.sms.service.SmsLogService;

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