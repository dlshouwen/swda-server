package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysRoleDataScopeEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * role data scope mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleDataScopeDao extends BaseMapper<SysRoleDataScopeEntity> {

	/**
	 * get organ id list
	 * @param roleId
	 * @return organ id list
	 */
	List<Long> getOrgIdList(@Param("roleId") Long roleId);

	/**
	 * get data scope list
	 * @param userId
	 * @return data scope list
	 */
	List<Long> getDataScopeList(@Param("userId") Long userId);

}