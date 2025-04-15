package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.DictType;
import com.dlshouwen.swda.bms.system.service.IDictTypeService;
import com.dlshouwen.swda.bms.system.vo.DictTypeVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dict type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/dict_type")
@Tag(name = "dict type")
@AllArgsConstructor
public class DictTypeController {
	
	/** dict type service */
	private final IDictTypeService dictTypeService;

	/**
	 * get dict type page result
	 * @param query
	 * @return dict type page result
	 */
	@PostMapping("/page")
	@Operation(name = "get dict type page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:dict_type:page')")
	public R<PageResult<DictTypeVO>> getDictTypePageResult(@ParameterObject @Valid Query<DictType> query) {
//		get dict type page result
		PageResult<DictTypeVO> pageResult = dictTypeService.getDictTypePageResult(query);
//		return dict type page result
		return R.ok(pageResult);
	}

	/**
	 * get dict type data
	 * @param dictTypeId
	 * @return result
	 */
	@GetMapping("/{dictTypeId}/data")
	@Operation(name = "get dict type data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:dict_type:data')")
	public R<DictTypeVO> getDictTypeData(@PathVariable("dictTypeId") Long dictTypeId) {
//		get dict type data
		DictTypeVO dictType = dictTypeService.getDictTypeData(dictTypeId);
//		return
		return R.ok(dictType);
	}

	/**
	 * add dict type
	 * @param dictTypeVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add dict type", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:dict_type:add')")
	public R<String> addDictType(@RequestBody @Valid DictTypeVO dictTypeVO) {
//		add dict type
		dictTypeService.addDictType(dictTypeVO);
//		return
		return R.ok();
	}

	/**
	 * update dict type
	 * @param dictTypeVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update dict type", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:dict_type:update')")
	public R<String> updateDictType(@RequestBody @Valid DictTypeVO dictTypeVO) {
//		update dict type
		dictTypeService.updateDictType(dictTypeVO);
//		return
		return R.ok();
	}

	/**
	 * delete dict type
	 * @param dictTypeIdList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete dict type", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:dict_type:delete')")
	public R<String> deleteDictType(@RequestBody List<Long> dictTypeIdList) {
//		delete dict type
		dictTypeService.deleteDictType(dictTypeIdList);
//		result
		return R.ok();
	}

	/**
	 * get sql dict list
	 * @param dictTypeId
	 * @return sql dict list
	 */
	@GetMapping("/{dictTypeId}/sql/dict/list")
	@Operation(name = "get sql dict list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:dict_type:sql:dict:list')")
	public R<List<DictVO>> getSqlDictList(@PathVariable(name="dictTypeId") Long dictTypeId) {
//		get dict list
		List<DictVO> dictList = dictTypeService.getSqlDictList(dictTypeId);
//		return
		return R.ok(dictList);
	}

	/**
	 * refresh dict trans cache
	 * @return result
	 */
	@GetMapping("/cache/refresh")
	@Operation(name = "refresh dict trans cache", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('sys:dict_type:cache:refresh')")
	public R<String> refreshDictTransCache() {
//		refresh dict trans cache
		dictTypeService.refreshDictTransCache();
//		result
		return R.ok();
	}

}