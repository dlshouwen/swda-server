package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysRoleConvert;
import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.query.SysRoleUserQuery;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.PremissionVO;
import com.dlshouwen.swda.bms.vo.RoleOrganVO;
import com.dlshouwen.swda.bms.vo.RoleVO;
import com.dlshouwen.swda.bms.vo.UserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * role
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/role")
@Tag(name = "role")
@AllArgsConstructor
public class SysRoleController {
	
	/** role service */
	private final SysRoleService sysRoleService;
	/** user service */
	private final SysUserService sysUserService;
	/** role menu service */
	private final SysRoleMenuService sysRoleMenuService;
	/** role data scope service */
	private final SysRoleDataScopeService sysRoleDataScopeService;
	/** menu service */
	private final SysMenuService sysMenuService;
	/** user role service */
	private final SysUserRoleService sysUserRoleService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:role:page')")
	public R<PageResult<RoleVO>> page(@ParameterObject @Valid SysRoleQuery query) {
//		page
		PageResult<RoleVO> page = sysRoleService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * list
	 * @return result
	 */
	@GetMapping("list")
	@Operation(name = "list")
	@PreAuthorize("hasAuthority('sys:role:list')")
	public R<List<RoleVO>> list() {
//		get list
		List<RoleVO> list = sysRoleService.getList(new SysRoleQuery());
//		result
		return R.ok(list);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "信息")
	@PreAuthorize("hasAuthority('sys:role:info')")
	public R<RoleVO> get(@PathVariable("id") Long id) {
//		get role by id
		Role entity = sysRoleService.getById(id);
//		convert to role vo
		RoleVO role = SysRoleConvert.INSTANCE.convert(entity);
//		get menu id list set to role
		List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
		role.setMenuIdList(menuIdList);
//		get organ id list set to role
		List<Long> orgIdList = sysRoleDataScopeService.getOrgIdList(id);
		role.setOrgIdList(orgIdList);
//		return
		return R.ok(role);
	}

	/**
	 * save
	 * @param roleVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:role:save')")
	public R<String> save(@RequestBody @Valid RoleVO vo) {
//		save
		sysRoleService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param roleVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> update(@RequestBody @Valid RoleVO vo) {
//		update
		sysRoleService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * data scope
	 * @param vo
	 * @return
	 */
	@PutMapping("data-scope")
	@Operation(name = "data scope", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> dataScope(@RequestBody @Valid RoleOrganVO vo) {
//		data scope
		sysRoleService.dataScope(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param idList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:role:delete')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysRoleService.delete(idList);
//		return
		return R.ok();
	}

	/**
	 * menu
	 * @return result
	 */
	@GetMapping("menu")
	@Operation(name = "menu")
	@PreAuthorize("hasAuthority('sys:role:menu')")
	public R<List<PremissionVO>> menu() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user menu list
		List<PremissionVO> list = sysMenuService.getUserMenuList(user, null);
//		return
		return R.ok(list);
	}

	/**
	 * user page
	 * @param query
	 * @return result
	 */
	@GetMapping("user/page")
	@Operation(name = "user page")
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<PageResult<UserVO>> userPage(@Valid SysRoleUserQuery query) {
//		role user page
		PageResult<UserVO> page = sysUserService.roleUserPage(query);
//		return
		return R.ok(page);
	}

	/**
	 * user delete
	 * @param roleId
	 * @param userIdList
	 * @return result
	 */
	@DeleteMapping("user/{roleId}")
	@Operation(name = "delete role user", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
//		delete role user
		sysUserRoleService.deleteByUserIdList(roleId, userIdList);
//		return
		return R.ok();
	}

	/**
	 * user save
	 * @param roleId
	 * @param userIdList
	 * @return result
	 */
	@PostMapping("user/{roleId}")
	@Operation(name = "user save", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
//		save role user
		sysUserRoleService.saveUserList(roleId, userIdList);
//		return
		return R.ok();
	}

	/**
	 * get name list
	 * @param idList
	 * @return result
	 */
	@PostMapping("nameList")
	@Operation(name = "get name list")
	public R<List<String>> nameList(@RequestBody List<Long> idList) {
//		get name list
		List<String> list = sysRoleService.getNameList(idList);
//		return
		return R.ok(list);
	}

}