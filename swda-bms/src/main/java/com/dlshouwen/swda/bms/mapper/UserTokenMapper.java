package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.UserToken;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * user token mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
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