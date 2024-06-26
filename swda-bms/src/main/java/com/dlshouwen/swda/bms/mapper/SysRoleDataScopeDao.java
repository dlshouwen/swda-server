package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysRoleDataScopeEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色数据权限
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleDataScopeDao extends BaseMapper<SysRoleDataScopeEntity> {

	/**
	 * 根据角色ID，获取机构ID列表
	 */
	List<Long> getOrgIdList(@Param("roleId") Long roleId);

	/**
	 * 获取用户的数据权限列表
	 */
	List<Long> getDataScopeList(@Param("userId") Long userId);

}