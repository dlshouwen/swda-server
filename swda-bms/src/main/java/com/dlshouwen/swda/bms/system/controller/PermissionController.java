package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.system.dict.PermissionType;
import com.dlshouwen.swda.bms.system.service.IPermissionService;
import com.dlshouwen.swda.bms.system.vo.PermissionVO;
import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * permission
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "permission")
@AllArgsConstructor
public class PermissionController {
	
	/** permission service */
	private final IPermissionService permissionService;

	/**
	 * get user menu list
	 * @return user menu list
	 */
	@GetMapping("/user/menu/list")
	@Operation(name = "get user menu list", type = OperateType.SEARCH)
	public R<List<PermissionVO>> getUserMenuList() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user menu list
		List<PermissionVO> userMenuList = permissionService.getUserMenuList(user, PermissionType.MENU);
//		return
		return R.ok(userMenuList);
	}

	/**
	 * get user authority list
	 * @return user authority list
	 */
	@GetMapping("/user/authority/list")
	@Operation(name = "get user authority list", type = OperateType.SEARCH)
	public R<Set<String>> getUserAuthorityList() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get user authority list
		Set<String> userAuthorityList = permissionService.getUserAuthorityList(user);
//		return
		return R.ok(userAuthorityList);
	}

	/**
	 * get permission list
	 * @param permissionType
	 * @return permission list
	 */
	@GetMapping("/list")
	@Operation(name = "get permission list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:permission:list')")
	public R<List<PermissionVO>> getPermissionList(Integer permissionType) {
//		get permission list
		List<PermissionVO> permissionList = permissionService.getPermissionList(permissionType);
//		return
		return R.ok(permissionList);
	}

	/**
	 * get permission data
	 * @param permissionId
	 * @return permission
	 */
	@GetMapping("/data/{permissionId}")
	@Operation(name = "get permission data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:permission:data')")
	public R<PermissionVO> getPermissionData(@PathVariable("permissionId") Long permissionId) {
//		get permission data
		PermissionVO permission = permissionService.getPermissionData(permissionId);
//		return
		return R.ok(permission);
	}

	/**
	 * add permission
	 * @param permissionVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add permission", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:permission:add')")
	public R<String> addPermission(@RequestBody @Valid PermissionVO permissionVO) {
//		add permission
		permissionService.addPermission(permissionVO);
//		return
		return R.ok();
	}

	/**
	 * update permission
	 * @param permissionVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update permission", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:permission:update')")
	public R<String> updatePermission(@RequestBody @Valid PermissionVO permissionVO) {
//		update permission
		permissionService.updatePermission(permissionVO);
//		return
		return R.ok();
	}

	/**
	 * delete permission
	 * @param permissionId
	 * @return result
	 */
	@DeleteMapping("/permission/{permissionId}")
	@Operation(name = "delete permission", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:permission:delete')")
	public R<String> deletePermission(@PathVariable("permissionId") Long permissionId) {
//		get sub menu count
		Long count = permissionService.getSubPermissionCount(permissionId);
//		if has sub menu
		if (count > 0) {
//			return
			return R.error("请先删除子菜单");
		}
//		delete permission
		permissionService.deletePermission(permissionId);
//		return
		return R.ok();
	}

}