package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.entity.System;
import com.dlshouwen.swda.bms.system.service.ISystemService;
import com.dlshouwen.swda.bms.system.vo.SystemVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * system
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/system")
@Tag(name = "system")
@AllArgsConstructor
public class SystemController {
	
	/** system service */
	private final ISystemService systemService;

	/**
	 * get login user system list
	 * @return login user system list
	 */
	@PostMapping("/login/user/system/list")
	@Operation(name = "get login user system list", type = OperateType.SEARCH)
	public R<List<SystemVO>> getLoginUserSystemList() {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get login user system list
		List<SystemVO> loginUserSystemList = systemService.getLoginUserSystemList(user);
//		return
		return R.ok(loginUserSystemList);
	}

	/**
	 * get system page result
	 * @param query
	 * @return system page result
	 */
	@PostMapping("/page")
	@Operation(name = "get system page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:system:page')")
	public R<PageResult<SystemVO>> getSystemPageResult(@RequestBody @Valid Query<System> query) {
//		get system page result
		PageResult<SystemVO> pageResult = systemService.getSystemPageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get system list
	 * @return post list
	 */
	@PostMapping("/list")
	@Operation(name = "get system list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:system:list')")
	public R<List<SystemVO>> getSystemList() {
//		get system list
		List<SystemVO> systemList = systemService.getSystemList();
//		return
		return R.ok(systemList);
	}

	/**
	 * get system data
	 * @param systemId
	 * @return system data
	 */
	@GetMapping("/{systemId}/data")
	@Operation(name = "get system data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:system:data')")
	public R<SystemVO> getSystemData(@PathVariable("systemId") Long systemId) {
//		get system data
		SystemVO system = systemService.getSystemData(systemId);
//		return system
		return R.ok(system);
	}

	/**
	 * add system
	 * @param systemVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add system", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:system:add')")
	public R<String> addSystem(@RequestBody @Valid SystemVO systemVO) {
//		add system
		systemService.addSystem(systemVO);
//		return
		return R.ok();
	}

	/**
	 * update system
	 * @param systemVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update system", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:system:update')")
	public R<String> updateSystem(@RequestBody @Valid SystemVO systemVO) {
//		update system
		systemService.updateSystem(systemVO);
//		return
		return R.ok();
	}

	/**
	 * delete system
	 * @param systemIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete system", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:system:delete')")
	public R<String> deleteSystem(@RequestBody List<Long> systemIdList) {
//		delete system
		systemService.deleteSystem(systemIdList);
//		return
		return R.ok();
	}

}