package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.entity.Role;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.service.IPermissionService;
import com.dlshouwen.swda.bms.system.service.IRoleOrganService;
import com.dlshouwen.swda.bms.system.service.IRolePermissionService;
import com.dlshouwen.swda.bms.system.service.IRoleService;
import com.dlshouwen.swda.bms.system.service.IUserRoleService;
import com.dlshouwen.swda.bms.system.service.IUserService;
import com.dlshouwen.swda.bms.system.vo.PermissionVO;
import com.dlshouwen.swda.bms.system.vo.RoleDataScopeVO;
import com.dlshouwen.swda.bms.system.vo.RoleVO;
import com.dlshouwen.swda.bms.system.vo.UserVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * role
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/role")
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
	 * get role page result
	 * @param query
	 * @return role page result
	 */
	@GetMapping("/page")
	@Operation(name = "get role page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:role:page')")
	public R<PageResult<RoleVO>> getRolePageResult(@ParameterObject @Valid Query<Role> query) {
//		get role page result
		PageResult<RoleVO> pageResult = roleService.getRolePageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get role list
	 * @return role list
	 */
	@GetMapping("/list")
	@Operation(name = "get role list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:role:list')")
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
	@Operation(name = "get role data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:role:data')")
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
	@PreAuthorize("hasAuthority('bms:system:role:add')")
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
	@PreAuthorize("hasAuthority('bms:system:role:update')")
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
	@PreAuthorize("hasAuthority('bms:system:role:data_scope/set')")
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
	@PreAuthorize("hasAuthority('bms:system:role:delete')")
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
	@Operation(name = "get user permission list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:role:user:permission:list')")
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
	@Operation(name = "get user list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:role:update')")
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
//		delete user role
		userRoleService.deleteUserRole(roleId, userIdList);
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
//		add user role
		userRoleService.deleteUserRole(roleId, userIdList);
//		return
		return R.ok();
	}

	/**
	 * get role name list
	 * @param roleIdList
	 * @return role name list
	 */
	@PostMapping("/name/list")
	@Operation(name = "get role name list", type = OperateType.SEARCH)
	public R<List<String>> getRoleNameList(@RequestBody List<Long> roleIdList) {
//		get role name list
		List<String> roleNameList = roleService.getRoleNameList(roleIdList);
//		return role name list
		return R.ok(roleNameList);
	}

}