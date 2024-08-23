package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.DictCategory;
import com.dlshouwen.swda.bms.system.service.IDictCategoryService;
import com.dlshouwen.swda.bms.system.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dict category
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dict_category")
@Tag(name = "dict category")
@AllArgsConstructor
public class DictCategoryController {
	
	/** dict category service */
	private final IDictCategoryService dictCategoryService;

	/**
	 * get dict category list
	 * @param query
	 * @return dict category list
	 */
	@GetMapping("/list")
	@Operation(name = "get dict category list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:dict_category:list')")
	public R<PageResult<DictCategoryVO>> getDictCategoryList(@ParameterObject @Valid Query<DictCategory> query) {
//		get dict category list
		PageResult<DictCategoryVO> pageResult = dictCategoryService.getDictCategoryList(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get dict category data
	 * @param dictCategoryId
	 * @return result
	 */
	@GetMapping("/data/{dictCategoryId}")
	@Operation(name = "get dict category data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:dict_category:data')")
	public R<DictCategoryVO> getDictCategoryData(@PathVariable("dictCategoryId") String dictCategoryId) {
//		get dict category data
		DictCategoryVO dictCategory = dictCategoryService.getDictCategoryData(dictCategoryId);
//		return
		return R.ok(dictCategory);
	}

	/**
	 * add dict category
	 * @param dictCategoryVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add dict category", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:dict_category:add')")
	public R<String> addDictCategory(@RequestBody @Valid DictCategoryVO dictCategoryVO) {
//		add dict category
		dictCategoryService.addDictCategory(dictCategoryVO);
//		return
		return R.ok();
	}

	/**
	 * update dict category
	 * @param dictCategoryVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update dict category", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:dict_category:update')")
	public R<String> updateDictCategory(@RequestBody @Valid DictCategoryVO dictCategoryVO) {
//		update dict category
		dictCategoryService.updateDictCategory(dictCategoryVO);
//		return
		return R.ok();
	}

	/**
	 * delete dict category
	 * @param dictCategoryIdList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete dict category", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:dict_category:delete')")
	public R<String> deleteDictCategory(@RequestBody List<Long> dictCategoryIdList) {
//		delete dict category
		dictCategoryService.deleteDictCategory(dictCategoryIdList);
//		result
		return R.ok();
	}

	/**
	 * get sql dict list
	 * @param dictCategoryId
	 * @return sql dict list
	 */
	@GetMapping("/sql/dict/list")
	@Operation(name = "get sql dict list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:dict_category:sql:dict:list')")
	public R<List<DictVO>> getSqlDictList(String dictCategoryId) {
//		get dict list
		List<DictVO> dictList = dictCategoryService.getSqlDictList(dictCategoryId);
//		return
		return R.ok(dictList);
	}

	/**
	 * refresh dict trans cache
	 * @return result
	 */
	@GetMapping("/cache/refresh")
	@Operation(name = "refresh dict trans cache", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('sys:dict_category:cache:refresh')")
	public R<String> refreshDictTransCache() {
//		refresh dict trans cache
		dictCategoryService.refreshDictTransCache();
//		result
		return R.ok();
	}

}