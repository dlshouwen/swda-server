package com.dlshouwen.swda.bms.permission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.permission.service.IOrganService;
import com.dlshouwen.swda.bms.permission.vo.OrganVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * organ
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/permission/organ")
@Tag(name = "organ")
@AllArgsConstructor
public class OrganController {
	
	/** organ service */
	private final IOrganService organService;

	/**
	 * get organ list
	 * @return organ list
	 */
	@PostMapping("/list")
	@Operation(name = "get organ list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:permission:organ:list')")
	public R<List<OrganVO>> getOrganList() {
//		get organ list
		List<OrganVO> organList = organService.getOrganList();
//		return organ list
		return R.ok(organList);
	}

	/**
	 * get organ data
	 * @param organId
	 * @return organ
	 */
	@GetMapping("/{organId}/data")
	@Operation(name = "get organ data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:permission:organ:data')")
	public R<OrganVO> getOrganData(@PathVariable("organId") Long organId) {
//		get organ data
		OrganVO organ = organService.getOrganData(organId);
//		return
		return R.ok(organ);
	}

	/**
	 * add organ
	 * @param organVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add organ", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:permission:organ:add')")
	public R<String> addOrgan(@RequestBody @Valid OrganVO organVO) {
//		add organ
		organService.addOrgan(organVO);
//		return
		return R.ok();
	}

	/**
	 * update organ
	 * @param organVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update organ", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:permission:organ:update')")
	public R<String> updateOrgan(@RequestBody @Valid OrganVO organVO) {
//		update organ
		organService.updateOrgan(organVO);
//		return
		return R.ok();
	}

	/**
	 * delete organ
	 * @param organId
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete organ", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:permission:organ:delete')")
	public R<String> deleteOrgan(Long organId) {
//		delete organ
		organService.deleteOrgan(organId);
//		return
		return R.ok();
	}

	/**
	 * get organ name list
	 * @param organIdList
	 * @return result
	 */
	@PostMapping("/name/list")
	@Operation(name = "get organ name list", type = OperateType.SEARCH)
	public R<List<String>> getOrganNameList(@RequestBody List<Long> organIdList) {
//		get organ name list
		List<String> list = organService.getOrganNameList(organIdList);
//		return
		return R.ok(list);
	}

}