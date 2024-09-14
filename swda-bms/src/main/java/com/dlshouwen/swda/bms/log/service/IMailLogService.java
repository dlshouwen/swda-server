package com.dlshouwen.swda.bms.log.service;

import java.util.List;

import com.dlshouwen.swda.bms.log.entity.MailLog;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * 邮件日志
 *
 * @author 阿沐 babamu@126.com
 */
public interface IMailLogService extends BaseService<MailLog> {

//    PageResult<SysMailLogVO> page(SysMailLogQuery query);

    void delete(List<Long> idList);
}