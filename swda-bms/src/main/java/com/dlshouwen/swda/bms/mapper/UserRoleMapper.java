package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.UserRole;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * user role
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * get role id list
	 * @param userId
	 * @return role id list
	 */
	List<Long> getRoleIdList(@Param("userId") Long userId);

}