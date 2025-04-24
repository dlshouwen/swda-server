package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Town;
import com.dlshouwen.swda.bms.system.service.ITownService;
import com.dlshouwen.swda.bms.system.vo.TownVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * town
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/town")
@Tag(name = "town")
@AllArgsConstructor
public class TownController {
	
	/** town service */
	private final ITownService townService;

	/**
	 * get town page result
	 * @param query
	 * @return town page result
	 */
	@PostMapping("/page")
	@Operation(name = "get town page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:town:page')")
	public R<PageResult<TownVO>> getTownPageResult(@RequestBody @Valid Query<Town> query) {
//		get town page result
		PageResult<TownVO> pageResult = townService.getTownPageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get town data
	 * @param townId
	 * @return town data
	 */
	@GetMapping("/{townId}/data")
	@Operation(name = "get town data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:town:data')")
	public R<TownVO> getTownData(@PathVariable("townId") Long townId) {
//		get town data
		TownVO town = townService.getTownData(townId);
//		return town
		return R.ok(town);
	}

	/**
	 * add town
	 * @param townVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add town", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:town:add')")
	public R<String> addTown(@RequestBody @Valid TownVO townVO) {
//		add town
		townService.addTown(townVO);
//		return
		return R.ok();
	}

	/**
	 * update town
	 * @param townVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update town", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:town:update')")
	public R<String> updateTown(@RequestBody @Valid TownVO townVO) {
//		update town
		townService.updateTown(townVO);
//		return
		return R.ok();
	}

	/**
	 * delete town
	 * @param townIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete town", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:town:delete')")
	public R<String> deleteTown(@RequestBody List<Long> townIdList) {
//		delete town
		townService.deleteTown(townIdList);
//		return
		return R.ok();
	}

}