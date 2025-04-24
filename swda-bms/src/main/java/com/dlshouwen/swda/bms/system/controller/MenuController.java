package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.system.dict.MenuType;
import com.dlshouwen.swda.bms.system.service.IMenuService;
import com.dlshouwen.swda.bms.system.vo.MenuVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * menu
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/menu")
@Tag(name = "menu")
@AllArgsConstructor
public class MenuController {
	
	/** menu service */
	private final IMenuService menuService;

	/**
	 * get login user menu list
	 * @return login user menu list
	 */
	@PostMapping("/login/user/menu/list")
	@Operation(name = "get login user menu list", type = OperateType.SEARCH)
	public R<List<MenuVO>> getUserMenuList() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get login user menu list
		List<MenuVO> loginUserMenuList = menuService.getLoginUserMenuList(user, MenuType.MENU);
//		return
		return R.ok(loginUserMenuList);
	}

	/**
	 * get login user authority list
	 * @return login user authority list
	 */
	@PostMapping("/login/user/authority/list")
	@Operation(name = "get login user authority list", type = OperateType.SEARCH)
	public R<Set<String>> getLoginUserAuthorityList() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get login user authority list
		Set<String> loginUserAuthorityList = menuService.getLoginUserAuthorityList(user);
//		return
		return R.ok(loginUserAuthorityList);
	}

	/**
	 * get menu list
	 * @param systemId
	 * @return menu list
	 */
	@PostMapping("/list")
	@Operation(name = "get menu list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:menu:list')")
	public R<List<MenuVO>> getMenuList(@RequestBody Long systemId) {
//		get menu list
		List<MenuVO> menuList = menuService.getMenuList(systemId);
//		return
		return R.ok(menuList);
	}

	/**
	 * get menu data
	 * @param menuId
	 * @return menu
	 */
	@GetMapping("/{menuId}/data")
	@Operation(name = "get menu data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:menu:data')")
	public R<MenuVO> getMenuData(@PathVariable("menuId") Long menuId) {
//		get menu data
		MenuVO menu = menuService.getMenuData(menuId);
//		return
		return R.ok(menu);
	}

	/**
	 * add menu
	 * @param menuVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add menu", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:menu:add')")
	public R<String> addMenu(@RequestBody @Valid MenuVO menuVO) {
//		add menu
		menuService.addMenu(menuVO);
//		return
		return R.ok();
	}

	/**
	 * update menu
	 * @param menuVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update menu", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:menu:update')")
	public R<String> updateMenu(@RequestBody @Valid MenuVO menuVO) {
//		update menu
		menuService.updateMenu(menuVO);
//		return
		return R.ok();
	}

	/**
	 * delete menu
	 * @param menuId
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete menu", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:menu:delete')")
	public R<String> deleteMenu(@RequestBody Long menuId) {
//		get sub menu count
		Long count = menuService.getSubMenuCount(menuId);
//		if has sub menu
		if (count > 0) {
//			return
			return R.error("请先删除子菜单");
		}
//		delete menu
		menuService.deleteMenu(menuId);
//		return
		return R.ok();
	}

}