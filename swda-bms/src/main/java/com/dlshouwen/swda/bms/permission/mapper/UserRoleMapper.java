package com.dlshouwen.swda.bms.permission.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.permission.entity.UserRole;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * user role
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * get role id list
	 * @param userId
	 * @return role id list
	 */
	List<Long> getRoleIdList(@Param("userId") Long userId);

}