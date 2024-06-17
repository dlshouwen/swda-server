package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysUserTokenEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户Token
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    /**
     * 根据角色ID，查询在线用户 access_token 列表
     *
     * @param roleId 角色ID
     * @param time   当前时间
     * @return 返回 access_token 列表
     */
    List<String> getOnlineAccessTokenListByRoleId(@Param("roleId") Long roleId, @Param("time") LocalDateTime time);

    /**
     * 根据用户ID，查询在线用户 access_token 列表
     *
     * @param userId 用户ID
     * @param time   当前时间
     * @return 返回 access_token 列表
     */
    List<String> getOnlineAccessTokenListByUserId(@Param("userId") Long userId, @Param("time") LocalDateTime time);

}