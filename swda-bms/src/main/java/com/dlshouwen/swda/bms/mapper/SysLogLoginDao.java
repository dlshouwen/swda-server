package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

/**
 * login log mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginDao extends BaseMapper<SysLogLoginEntity> {

}