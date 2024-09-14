package com.dlshouwen.swda.bms.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.bms.log.entity.EmailLog;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

/**
 * email log mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper
public interface EmailLogMapper extends BaseMapper<EmailLog> {

}