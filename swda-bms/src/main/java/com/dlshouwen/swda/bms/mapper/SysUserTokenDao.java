package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysUserTokenEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * user token mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

	/**
	 * get online access token list by role id
	 * @param roleId
	 * @param time
	 * @return token list
	 */
	List<String> getOnlineAccessTokenListByRoleId(@Param("roleId") Long roleId, @Param("time") LocalDateTime time);

	/**
	 * get online access token list by user id
	 * @param userId
	 * @param time
	 * @return token list
	 */
	List<String> getOnlineAccessTokenListByUserId(@Param("userId") Long userId, @Param("time") LocalDateTime time);

}