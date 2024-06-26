package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

	/**
	 * 根据用户ID，获取用户最大的数据范围
	 */
	Integer getDataScopeByUserId(@Param("userId") Long userId);

	/**
	 * 根据用户ID，获取用户角色编码
	 */
	List<String> geRoleCodeByUserId(@Param("userId") Long userId);

}
