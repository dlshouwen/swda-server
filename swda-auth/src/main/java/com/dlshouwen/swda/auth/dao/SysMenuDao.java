package com.dlshouwen.swda.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.auth.entity.SysMenuEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * menu mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

	/**
	 * get menu list
	 * @param type
	 * @return menu list
	 */
	List<SysMenuEntity> getMenuList(@Param("type") Integer type);

	/**
	 * user menu list
	 * @param userId
	 * @param type
	 * @return menu list
	 */
	List<SysMenuEntity> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

	/**
	 * get user authority list
	 * @param userId
	 * @return authority list
	 */
	List<String> getUserAuthorityList(@Param("userId") Long userId);

	/**
	 * get authority list
	 * @return authority list
	 */
	List<String> getAuthorityList();

}
