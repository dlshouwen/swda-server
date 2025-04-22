package com.dlshouwen.swda.bms.platform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.platform.entity.AuthPlatform;
import com.dlshouwen.swda.bms.platform.service.IAuthPlatformService;
import com.dlshouwen.swda.bms.platform.vo.AuthPlatformVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * auth platform
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/platform/auth_platform")
@Tag(name = "auth platform")
@AllArgsConstructor
public class AuthPlatformController {
	
	/** auth platform service */
	private final IAuthPlatformService authPlatformService;

	/**
	 * get auth platform page result
	 * @param query
	 * @return auth platform page result
	 */
	@PostMapping("/page")
	@Operation(name = "get auth platform page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:auth_platform:page')")
	public R<PageResult<AuthPlatformVO>> getAuthPlatformPageResult(@RequestBody @Valid Query<AuthPlatform> query) {
//		get auth platform page result
		PageResult<AuthPlatformVO> pageResult = authPlatformService.getAuthPlatformPageResult(query);
//		return auth platform list
		return R.ok(pageResult);
	}

	/**
	 * get auth platform data
	 * @param authPlatformId
	 * @return auth platform
	 */
	@GetMapping("/{authPlatformId}/data")
	@Operation(name = "get auth platform data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:auth_platform:data')")
	public R<AuthPlatformVO> getAuthPlatformData(@PathVariable("authPlatformId") Long authPlatformId) {
//		get auth platform data
		AuthPlatformVO authPlatform = authPlatformService.getAuthPlatformData(authPlatformId);
//		return auth platform
		return R.ok(authPlatform);
	}

	/**
	 * add auth platform
	 * @param authPlatformVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add auth platform", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:platform:auth_platform:add')")
	public R<String> addAuthPlatform(@RequestBody AuthPlatformVO authPlatformVO) {
//		add auth platform
		authPlatformService.addAuthPlatform(authPlatformVO);
//		return
		return R.ok();
	}

	/**
	 * update auth platform
	 * @param authPlatformVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update auth platform", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:platform:auth_platform:update')")
	public R<String> updateAuthPlatform(@RequestBody @Valid AuthPlatformVO authPlatformVO) {
//		update auth platform
		authPlatformService.updateAuthPlatform(authPlatformVO);
//		return
		return R.ok();
	}

	/**
	 * delete auth platform
	 * @param authPlatformIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete auth platform", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:platform:auth_platform:delete')")
	public R<String> deleteAuthPlatform(@RequestBody List<Long> authPlatformIdList) {
//		delete auth platform
		authPlatformService.deleteAuthPlatform(authPlatformIdList);
//		return
		return R.ok();
	}

}