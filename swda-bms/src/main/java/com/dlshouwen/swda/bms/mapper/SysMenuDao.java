package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * menu mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysMenuDao extends BaseMapper<Permission> {

	/**
	 * get menu list
	 * @param type
	 * @return menu list
	 */
	List<Permission> getMenuList(@Param("type") Integer type);

	/**
	 * get user menu list
	 * @param userId
	 * @param type
	 * @return menu list
	 */
	List<Permission> getUserMenuList(@Param("userId") Long userId, @Param("type") Integer type);

	/**
	 * user authority list
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
