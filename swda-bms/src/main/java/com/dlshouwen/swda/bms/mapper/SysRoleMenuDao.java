package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysRoleMenuEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> getMenuIdList(@Param("roleId") Long roleId);
}
