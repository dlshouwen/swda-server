package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.RolePermission;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * role permission mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

	/**
	 * get permission id list
	 * @param roleId
	 * @return permission id list
	 */
	List<Long> getPermissionIdList(@Param("roleId") Long roleId);

}
