package com.dlshouwen.swda.bms.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

/**
 * operation log mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}