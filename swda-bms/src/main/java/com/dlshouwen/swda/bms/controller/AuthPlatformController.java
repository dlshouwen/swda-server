package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.entity.AuthPlatform;
import com.dlshouwen.swda.bms.service.IAuthPlatformService;
import com.dlshouwen.swda.bms.vo.AuthPlatformVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * auth platform
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("auth_platform")
@Tag(name = "auth platform")
@AllArgsConstructor
public class AuthPlatformController {
	
	/** auth platform service */
	private final IAuthPlatformService authPlatformService;

	/**
	 * get auth platform list
	 * @param query
	 * @return auth platform list
	 */
	@GetMapping("/list")
	@Operation(name = "get auth platform list")
	@PreAuthorize("hasAuthority('bms:auth_platform:list')")
	public R<PageResult<AuthPlatformVO>> getAuthPlatformList(@ParameterObject @Valid Query<AuthPlatform> query) {
//		get auth platform list
		PageResult<AuthPlatformVO> pageResult = authPlatformService.getAuthPlatformList(query);
//		return auth platform list
		return R.ok(pageResult);
	}

	/**
	 * get auth platform data
	 * @param authPlatformId
	 * @return auth platform
	 */
	@GetMapping("/data/{authPlatformId}")
	@Operation(name = "get auth platform data")
	@PreAuthorize("hasAuthority('bms:auth_platform:data')")
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
	@Operation(name = "add auth platform")
	@PreAuthorize("hasAuthority('bms:auth_platform:add')")
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
	@Operation(name = "update auth platform")
	@PreAuthorize("hasAuthority('bms:auth_platform:update')")
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
	@Operation(name = "delete auth platform")
	@PreAuthorize("hasAuthority('bms:auth_platform:delete')")
	public R<String> deleteAuthPlatform(@RequestBody List<Long> authPlatformIdList) {
//		delete auth platform
		authPlatformService.deleteAuthPlatform(authPlatformIdList);
//		return
		return R.ok();
	}

}