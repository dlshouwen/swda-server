package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.entity.DictCategory;
import com.dlshouwen.swda.bms.service.IDictCategoryService;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.vo.DictVO;

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
	@Operation(name = "get dict category list")
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
	@Operation(name = "get dict category data")
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
	@Operation(name = "get sql dict list")
	@PreAuthorize("hasAuthority('bms:dict_category:sql:dict:list')")
	public R<List<DictVO>> getSqlDictList(String dictCategoryId) {
//		get dict list
		List<DictVO> dictList = dictCategoryService.getSqlDictList(dictCategoryId);
//		return
		return R.ok(dictList);
	}

	/**
	 * refresh trans cache
	 * @return result
	 */
	@GetMapping("/cache/refresh")
	@Operation(name = "refresh trans cache")
	@PreAuthorize("hasAuthority('sys:dict_category:cache:refresh')")
	public R<String> refreshTransCache() {
//		refresh trans cache
		dictCategoryService.refreshTransCache();
//		result
		return R.ok();
	}

}