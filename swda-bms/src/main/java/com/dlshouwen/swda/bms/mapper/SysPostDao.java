package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.bms.entity.Post;
import com.dlshouwen.swda.core.mapper.BaseMapper;

/**
 * post mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysPostDao extends BaseMapper<Post> {

}