package com.dlshouwen.swda.bms.auth.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.auth.entity.UserToken;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * user token mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface UserTokenMapper extends BaseMapper<UserToken> {

	/**
	 * get online access token list by role id
	 * @param roleId
	 * @param expireTime
	 * @return token list
	 */
	List<String> getOnlineAccessTokenListByRoleId(@Param("roleId") Long roleId, @Param("expireTime") LocalDateTime expireTime);

	/**
	 * get online access token list by user id
	 * @param userId
	 * @param expireTime
	 * @return token list
	 */
	List<String> getOnlineAccessTokenListByUserId(@Param("userId") Long userId, @Param("expireTime") LocalDateTime expireTime);

}