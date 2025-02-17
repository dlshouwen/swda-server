package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.Permission;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * permission mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface PermissionMapper extends BaseMapper<Permission> {

	/**
	 * get permission list
	 * @param permissionType
	 * @return permission list
	 */
	List<Permission> getPermissionList(@Param("permissionType") Integer permissionType);

	/**
	 * get login user permission list
	 * @param userId
	 * @param permissionType
	 * @return login user permission list
	 */
	List<Permission> getLoginUserPermissionList(@Param("userId") Long userId, @Param("permissionType") Integer permissionType);

	/**
	 * user authority list
	 * @param userId
	 * @return authority list
	 */
	List<String> getLoginUserAuthorityList(@Param("userId") Long userId);

	/**
	 * get authority list
	 * @return authority list
	 */
	List<String> getAuthorityList();

}
