package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.bms.system.entity.Menu;
import com.dlshouwen.swda.bms.system.vo.MenuVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.core.security.user.UserDetail;

import java.util.List;
import java.util.Set;

/**
 * menu service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IMenuService extends BaseService<Menu> {

	/**
	 * get login user menu list
	 * @param user
	 * @param menuType
	 * @return login user menu list
	 */
	List<MenuVO> getLoginUserMenuList(UserDetail user, Integer menuType);
	
	/**
	 * get login user authority list
	 * @param user
	 * @return login user authority list
	 */
	Set<String> getLoginUserAuthorityList(UserDetail user);

	/**
	 * get menu list
	 * @param menuType
	 * @return menu vo list
	 */
	List<MenuVO> getMenuList(Integer menuType);
	
	/**
	 * get menu data
	 * @param menuId
	 * @return menu
	 */
	MenuVO getMenuData(Long menuId);

	/**
	 * add menu
	 * @param menuVO
	 */
	void addMenu(MenuVO menuVO);

	/**
	 * update menu
	 * @param menuVO
	 */
	void updateMenu(MenuVO menuVO);

	/**
	 * delete menu
	 * @param menuId
	 */
	void deleteMenu(Long menuId);

	/**
	 * get sub menu count
	 * @param preMenuId
	 * @return sub menu count
	 */
	Long getSubMenuCount(Long preMenuId);

}
