package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.Role;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * role mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * get data scope by user id
	 * @param userId
	 * @return data scope
	 */
	Integer getDataScopeByUserId(@Param("userId") Long userId);

	/**
	 * get role code by user id
	 * @param userId
	 * @return role code list
	 */
	List<String> geRoleCodeByUserId(@Param("userId") Long userId);

}