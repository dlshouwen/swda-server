package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.entity.PageResult;
import com.dlshouwen.swda.core.grid.entity.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.service.IDictService;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dict
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("dict")
@Tag(name = "dict")
@AllArgsConstructor
public class DictController {
	
	/** dict service */
	private final IDictService dictService;

	/**
	 * get dict list
	 * @param query
	 * @return result
	 */
	@GetMapping("/list/{dictCategoryId}")
	@Operation(name = "get dict list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:dict:list')")
	public R<PageResult<DictVO>> getDictList(@ParameterObject @Valid Query<Dict> query) {
//		get dict list
		PageResult<DictVO> pageResult = dictService.getDictList(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get dict data
	 * @param dictCategoryId
	 * @param dictId
	 * @return dict
	 */
	@GetMapping("/data/{dictCategoryId}/{dictId}")
	@Operation(name = "get dict data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:dict:data')")
	public R<DictVO> getDictData(@PathVariable("dictCategoryId") String dictCategoryId, @PathVariable("dictId") String dictId) {
//		get dict data
		DictVO dict = dictService.getDictData(dictCategoryId, dictId);
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
	@PreAuthorize("hasAuthority('bms:dict:add')")
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
	@PutMapping("/update")
	@Operation(name = "update dict", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:dict:update')")
	public R<String> updateDict(@RequestBody @Valid DictVO dictVO) {
//		update dict
		dictService.updateDict(dictVO);
//		return
		return R.ok();
	}

	/**
	 * delete dict
	 * @param dictCategoryId
	 * @param dictIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete dict", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:dict:delete')")
	public R<String> deleteDict(String dictCategoryId, @RequestBody List<String> dictIdList) {
//		delete dict
		dictService.deleteDict(dictCategoryId, dictIdList);
//		return
		return R.ok();
	}

}