package com.dlshouwen.swda.bms.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

/**
 * login log mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}