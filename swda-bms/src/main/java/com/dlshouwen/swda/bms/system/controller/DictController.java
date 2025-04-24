package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.service.IDictService;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dict
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/dict")
@Tag(name = "dict")
@AllArgsConstructor
public class DictController {
	
	/** dict service */
	private final IDictService dictService;

	/**
	 * get dict page result
	 * @param dictType
	 * @param query
	 * @return dict page result
	 */
	@PostMapping("/page/{dictType}")
	@Operation(name = "get dict page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:dict:page')")
	public R<PageResult<DictVO>> getDictPageResult(@PathVariable(name = "dictType") String dictType, @RequestBody @Valid Query<Dict> query) {
//		get dict page result
		PageResult<DictVO> pageResult = dictService.getDictPageResult(dictType, query);
//		return dict page result
		return R.ok(pageResult);
	}

	/**
	 * get dict data
	 * @param dictId
	 * @return dict
	 */
	@GetMapping("/{dictId}/data")
	@Operation(name = "get dict data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:dict:data')")
	public R<DictVO> getDictData(@PathVariable("dictId") Long dictId) {
//		get dict data
		DictVO dict = dictService.getDictData(dictId);
//		return dict
		return R.ok(dict);
	}

	/**
	 * add dict
	 * @param dictVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add dict", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:dict:add')")
	public R<String> addDict(@RequestBody @Valid DictVO dictVO) {
//		add dict
		dictService.addDict(dictVO);
//		return
		return R.ok();
	}

	/**
	 * update dict
	 * @param dictVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update dict", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:dict:update')")
	public R<String> updateDict(@RequestBody @Valid DictVO dictVO) {
//		update dict
		dictService.updateDict(dictVO);
//		return
		return R.ok();
	}

	/**
	 * delete dict
	 * @param dictIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete dict", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:dict:delete')")
	public R<String> deleteDict(@RequestBody List<Long> dictIdList) {
//		delete dict
		dictService.deleteDict(dictIdList);
//		return
		return R.ok();
	}

}