package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysUserPostEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
* 用户岗位关系
*
* @author 阿沐 babamu@126.com
*/
@Mapper
public interface SysUserPostDao extends BaseMapper<SysUserPostEntity> {

    /**
     * 岗位ID列表
     * @param userId  用户ID
     */
    List<Long> getPostIdList(@Param("userId") Long userId);
}