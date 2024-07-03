package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.PermissionVO;
import com.dlshouwen.swda.bms.vo.RoleDataScopeVO;
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
@RequestMapping("role")
@Tag(name = "role")
@AllArgsConstructor
public class RoleController {
	
	/** role service */
	private final IRoleService roleService;
	/** user service */
	private final IUserService userService;
	/** role menu service */
	private final IRolePermissionService rolePermissionService;
	/** role data scope service */
	private final IRoleOrganService roleOrganService;
	/** menu service */
	private final IPermissionService permissionService;
	/** user role service */
	private final IUserRoleService userRoleService;

	/**
	 * get role list
	 * @param query
	 * @return role list
	 */
	@GetMapping("/list")
	@Operation(name = "get role list")
	@PreAuthorize("hasAuthority('bms:role:list')")
	public R<PageResult<RoleVO>> getRoleList(@ParameterObject @Valid Query<Role> query) {
//		get role list
		PageResult<RoleVO> roleList = roleService.getRoleList(query);
//		return
		return R.ok(roleList);
	}

	/**
	 * get role list
	 * @return role list
	 */
	@GetMapping("/list")
	@Operation(name = "get role list")
	@PreAuthorize("hasAuthority('bms:role:list')")
	public R<List<RoleVO>> getRoleList() {
//		get role list
		List<RoleVO> roleList = roleService.getRoleList();
//		result
		return R.ok(roleList);
	}

	/**
	 * get role data
	 * @param roleId
	 * @return role data
	 */
	@GetMapping("/data/{roleId}")
	@Operation(name = "get role data")
	@PreAuthorize("hasAuthority('bms:role:data')")
	public R<RoleVO> getRoleData(@PathVariable("roleId") Long roleId) {
//		get role data
		RoleVO role = roleService.getRoleData(roleId);
//		get permission id list set to role
		List<Long> permissionIdList = rolePermissionService.getPermissionIdList(roleId);
		role.setPermissionIdList(permissionIdList);
//		get organ id list set to role
		List<Long> organIdList = roleOrganService.getOrganIdList(roleId);
		role.setOrganIdList(organIdList);
//		return role
		return R.ok(role);
	}

	/**
	 * add role
	 * @param roleVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add role", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:role:add')")
	public R<String> addRole(@RequestBody @Valid RoleVO roleVO) {
//		add role
		roleService.addRole(roleVO);
//		return
		return R.ok();
	}

	/**
	 * update role
	 * @param roleVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update role", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:role:update')")
	public R<String> updateRole(@RequestBody @Valid RoleVO roleVO) {
//		update role
		roleService.updateRole(roleVO);
//		return
		return R.ok();
	}

	/**
	 * set role data scope
	 * @param roleDataScopeVO
	 * @return result
	 */
	@PutMapping("/data_scope/set")
	@Operation(name = "set role data scope", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:role:data_scope/set')")
	public R<String> setRoleDataScope(@RequestBody @Valid RoleDataScopeVO roleDataScopeVO) {
//		set role data scope
		roleService.setRoleDataScope(roleDataScopeVO);
//		return
		return R.ok();
	}

	/**
	 * delete role
	 * @param roleIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete role", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:role:delete')")
	public R<String> deleteRole(@RequestBody List<Long> roleIdList) {
//		delete role
		roleService.deleteRole(roleIdList);
//		return
		return R.ok();
	}

	/**
	 * get user permission list
	 * @return result
	 */
	@GetMapping("/user/permission/list")
	@Operation(name = "get user permission list")
	@PreAuthorize("hasAuthority('bms:role:user:permission:list')")
	public R<List<PermissionVO>> menu() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user permission list
		List<PermissionVO> permissionList = permissionService.getUserMenuList(user, null);
//		return
		return R.ok(permissionList);
	}

	/**
	 * get user list
	 * @param query
	 * @return result
	 */
	@GetMapping("/user/list")
	@Operation(name = "get user list")
	@PreAuthorize("hasAuthority('bms:role:update')")
	public R<PageResult<UserVO>> getUserList(@Valid Query<User> query) {
//		get user list
		PageResult<UserVO> pageResult = userService.getRoleUserList(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * delete role user
	 * @param roleId
	 * @param userIdList
	 * @return result
	 */
	@DeleteMapping("/{roleId}/user/delete")
	@Operation(name = "delete role user", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> deleteRoleUser(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
//		delete role user
		userRoleService.deleteRoleUser(roleId, userIdList);
//		return
		return R.ok();
	}

	/**
	 * add role user
	 * @param roleId
	 * @param userIdList
	 * @return result
	 */
	@PostMapping("/{roleId}/user/add")
	@Operation(name = "add role user", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:role:update')")
	public R<String> addRoleUser(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
//		add role user
		userRoleService.addRoleUser(roleId, userIdList);
//		return
		return R.ok();
	}

	/**
	 * get role name list
	 * @param roleIdList
	 * @return role name list
	 */
	@PostMapping("/name/list")
	@Operation(name = "get role name list")
	public R<List<String>> getRoleNameList(@RequestBody List<Long> roleIdList) {
//		get role name list
		List<String> roleNameList = roleService.getRoleNameList(roleIdList);
//		return role name list
		return R.ok(roleNameList);
	}

}