package com.dlshouwen.swda.bms.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.permission.service.IRoleMenuService;
import com.dlshouwen.swda.bms.system.convert.MenuConvert;
import com.dlshouwen.swda.bms.system.entity.Menu;
import com.dlshouwen.swda.bms.system.mapper.MenuMapper;
import com.dlshouwen.swda.bms.system.service.IMenuService;
import com.dlshouwen.swda.bms.system.vo.MenuVO;
import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.TreeUtils;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * menu service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {
	
	/** role menu service */
	private final IRoleMenuService roleMenuService;

	/**
	 * get login user menu list
	 * @param user
	 * @param menuType
	 * @return login user menu list
	 */
	@Override
	public List<MenuVO> getLoginUserMenuList(UserDetail user, Integer menuType) {
//		defined menu list
		List<Menu> menuList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get menu list
			menuList = baseMapper.getMenuList(menuType);
		} else {
//			get login user menu list
			menuList = baseMapper.getLoginUserMenuList(user.getUserId(), menuType);
		}
//		build menu tree for return
		return TreeUtils.build(MenuConvert.INSTANCE.convert2VOList(menuList));
	}

	/**
	 * get login user authority list
	 * @param user
	 * @return login user authority list
	 */
	@Override
	public Set<String> getLoginUserAuthorityList(UserDetail user) {
//		defined authority list
		List<String> authorityList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get authority list
			authorityList = baseMapper.getAuthorityList();
		} else {
//			get user authority list
			authorityList = baseMapper.getLoginUserAuthorityList(user.getUserId());
		}
//		defined perms set
		Set<String> permsSet = new HashSet<>();
//		for each authority
		for (String authority : authorityList) {
//			blank then continue
			if (StrUtil.isBlank(authority)) {
				continue;
			}
//			add to perms
			permsSet.addAll(Arrays.asList(authority.trim().split(",")));
		}
//		return menu set
		return permsSet;
	}

	/**
	 * get menu list
	 * @param menuType
	 * @return menu list
	 */
	@Override
	public List<MenuVO> getMenuList(Integer menuType) {
//		get menu list
		List<Menu> menuList = baseMapper.getMenuList(menuType);
//		build menu tree for return
		return TreeUtils.build(MenuConvert.INSTANCE.convert2VOList(menuList));
	}
	
	/**
	 * get menu data
	 * @param menuId
	 * @return menu data
	 */
	@Override
	public MenuVO getMenuData(Long menuId) {
//		get menu data
		Menu menu = this.getById(menuId);
//		convert to vo for return
		return MenuConvert.INSTANCE.convert2VO(menu);
	}

	/**
	 * add menu
	 * @param menuVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addMenu(MenuVO menuVO) {
//		convert to menu
		Menu menu = MenuConvert.INSTANCE.convert(menuVO);
//		insert menu
		this.save(menu);
	}

	/**
	 * update menu
	 * @param menuVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMenu(MenuVO menuVO) {
//		convert to menu
		Menu menu = MenuConvert.INSTANCE.convert(menuVO);
//		if menu id equals pre menu id
		if (menu.getMenuId().equals(menu.getPreMenuId())) {
//			throw exception
			throw new SwdaException("上级菜单不能为自己");
		}
//		update menu
		this.updateById(menu);
	}

	/**
	 * delete menu
	 * @param menuId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteMenu(Long menuId) {
//		delete menu
		this.removeById(menuId);
//		delete role menu
		roleMenuService.deleteRoleMenuByMenuId(menuId);
	}

	/**
	 * get sub menu count
	 * @param preMenuId
	 * @return sub menu count
	 */
	@Override
	public Long getSubMenuCount(Long preMenuId) {
		return this.count(Wrappers.<Menu>lambdaQuery().eq(Menu::getPreMenuId, preMenuId));
	}

}