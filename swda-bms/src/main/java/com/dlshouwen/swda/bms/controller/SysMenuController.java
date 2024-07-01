package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.convert.SysMenuConvert;
import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.bms.enums.MenuTypeEnum;
import com.dlshouwen.swda.bms.service.SysMenuService;
import com.dlshouwen.swda.bms.vo.PermissionVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * menu
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/menu")
@Tag(name = "menu")
@AllArgsConstructor
public class SysMenuController {
	
	/** menu service */
	private final SysMenuService sysMenuService;

	/**
	 * nav
	 * @return result
	 */
	@GetMapping("nav")
	@Operation(name = "nav")
	public R<List<PermissionVO>> nav() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user menu list
		List<PermissionVO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());
//		return
		return R.ok(list);
	}

	/**
	 * authority
	 * @return result
	 */
	@GetMapping("authority")
	@Operation(name = "authority")
	public R<Set<String>> authority() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user authority
		Set<String> set = sysMenuService.getUserAuthority(user);
//		return
		return R.ok(set);
	}

	/**
	 * list
	 * @param type
	 * @return result
	 */
	@GetMapping("list")
	@Operation(name = "list")
	@Parameter(name = "type", description = "0:menu 1:button 2:interface null:all")
	@PreAuthorize("hasAuthority('sys:menu:list')")
	public R<List<PermissionVO>> list(Integer type) {
//		get menu list
		List<PermissionVO> list = sysMenuService.getMenuList(type);
//		return
		return R.ok(list);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "get")
	@PreAuthorize("hasAuthority('sys:menu:info')")
	public R<PermissionVO> get(@PathVariable("id") Long id) {
//		get menu by id
		Permission entity = sysMenuService.getById(id);
//		convert to menu vo
		PermissionVO vo = SysMenuConvert.INSTANCE.convert(entity);
//		is has parent menu
		if (entity.getPid() != null) {
//			get parent menu
			Permission parentEntity = sysMenuService.getById(entity.getPid());
//			set parent name
			vo.setParentName(parentEntity.getName());
		}
//		return
		return R.ok(vo);
	}

	/**
	 * save
	 * @param menuVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:menu:save')")
	public R<String> save(@RequestBody @Valid PermissionVO vo) {
//		save
		sysMenuService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param menuVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:menu:update')")
	public R<String> update(@RequestBody @Valid PermissionVO vo) {
//		update
		sysMenuService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param id
	 * @return result
	 */
	@DeleteMapping("{id}")
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:menu:delete')")
	public R<String> delete(@PathVariable("id") Long id) {
//		get sub menu count
		Long count = sysMenuService.getSubMenuCount(id);
//		if has sub menu
		if (count > 0) {
//			return
			return R.error("请先删除子菜单");
		}
//		delete
		sysMenuService.delete(id);
//		return
		return R.ok();
	}

}