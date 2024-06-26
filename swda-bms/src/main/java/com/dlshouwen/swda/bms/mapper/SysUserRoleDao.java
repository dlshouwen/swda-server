package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysUserRoleEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * user role
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

	/**
	 * get role id list
	 * @param userId
	 * @return role id list
	 */
	List<Long> getRoleIdList(@Param("userId") Long userId);

}