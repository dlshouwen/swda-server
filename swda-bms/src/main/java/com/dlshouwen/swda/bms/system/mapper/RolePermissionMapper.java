package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.RolePermission;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * role permission mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

	/**
	 * get permission id list
	 * @param roleId
	 * @return permission id list
	 */
	List<Long> getPermissionIdList(@Param("roleId") Long roleId);

}
