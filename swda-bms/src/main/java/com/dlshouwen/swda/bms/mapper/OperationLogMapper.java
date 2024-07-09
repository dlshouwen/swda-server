package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.core.entity.log.OperationLog;
import com.dlshouwen.swda.core.mapper.BaseMapper;

/**
 * operation log mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}