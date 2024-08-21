package com.dlshouwen.swda.bms.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

/**
 * operation log mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface DataLogMapper extends BaseMapper<DataLog> {

}