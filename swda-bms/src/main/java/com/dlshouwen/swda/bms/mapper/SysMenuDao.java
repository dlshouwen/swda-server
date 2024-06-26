package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysMenuEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * 菜单管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

	/**
	 * 查询所有菜单列表
	 *
	 * @param type 菜单类型
	 */
	List<SysMenuEntity> getMenuList(@Param("type") Integer type);

	/**
	 * 查询用户菜单列表
	 *
	 * @param userId 用户ID
	 * @param type   菜单类型
	 */
	List<SysMenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

	/**
	 * 查询用户权限列表
	 * 
	 * @param userId 用户ID
	 */
	List<String> getUserAuthorityList(@Param("userId") Long userId);

	/**
	 * 查询所有权限列表
	 */
	List<String> getAuthorityList();

}
