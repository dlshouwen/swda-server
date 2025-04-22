package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.Menu;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * menu mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * get menu list
	 * @param menuType
	 * @return menu list
	 */
	List<Menu> getMenuList(@Param("menuType") String menuType);

	/**
	 * get login user menu list
	 * @param userId
	 * @param menuType
	 * @return login user menu list
	 */
	List<Menu> getLoginUserMenuList(@Param("userId") Long userId, @Param("menuType") String menuType);

	/**
	 * user authority list
	 * @param userId
	 * @return authority list
	 */
	List<String> getLoginUserAuthorityList(@Param("userId") Long userId);

	/**
	 * get authority list
	 * @return authority list
	 */
	List<String> getAuthorityList();

}
