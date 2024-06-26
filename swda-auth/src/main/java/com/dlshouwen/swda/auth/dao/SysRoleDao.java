package com.dlshouwen.swda.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.auth.entity.SysRoleEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * role mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

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
	List<String> getRoleCodeByUserId(@Param("userId") Long userId);

}
