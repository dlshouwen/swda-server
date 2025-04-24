package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.system.service.IRegionService;
import com.dlshouwen.swda.bms.system.vo.RegionVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * region
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/region")
@Tag(name = "region")
@AllArgsConstructor
public class RegionController {
	
	/** region service */
	private final IRegionService regionService;

	/**
	 * get region list
	 * @param regionId
	 * @return region list
	 */
	@PostMapping("/list")
	@Operation(name = "get region list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:region:list')")
	public R<List<RegionVO>> getRegionList(@RequestBody Integer regionId) {
//		get region list
		List<RegionVO> regionList = regionService.getRegionList(regionId);
//		return
		return R.ok(regionList);
	}

	/**
	 * get region data
	 * @param regionId
	 * @return region
	 */
	@GetMapping("/{regionId}/data")
	@Operation(name = "get region data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:region:data')")
	public R<RegionVO> getRegionData(@PathVariable("regionId") Integer regionId) {
//		get region data
		RegionVO region = regionService.getRegionData(regionId);
//		return
		return R.ok(region);
	}

	/**
	 * add region
	 * @param regionVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add region", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:region:add')")
	public R<String> addRegion(@RequestBody @Valid RegionVO regionVO) {
//		add region
		regionService.addRegion(regionVO);
//		return
		return R.ok();
	}

	/**
	 * update region
	 * @param regionVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update region", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:region:update')")
	public R<String> updateRegion(@RequestBody @Valid RegionVO regionVO) {
//		update region
		regionService.updateRegion(regionVO);
//		return
		return R.ok();
	}

	/**
	 * delete region
	 * @param regionId
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete region", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:region:delete')")
	public R<String> deleteRegion(@RequestBody Integer regionId) {
//		get sub region count
		Long count = regionService.getSubRegionCount(regionId);
//		if has sub region
		if (count > 0) {
//			return
			return R.error("请先删除子区域");
		}
//		delete region
		regionService.deleteRegion(regionId);
//		return
		return R.ok();
	}

}