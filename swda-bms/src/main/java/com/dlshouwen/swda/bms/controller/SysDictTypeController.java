package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysDictTypeConvert;
import com.dlshouwen.swda.bms.entity.SysDictTypeEntity;
import com.dlshouwen.swda.bms.query.SysDictTypeQuery;
import com.dlshouwen.swda.bms.service.SysDictTypeService;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.vo.SysDictVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dict type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/dict/type")
@Tag(name = "dict type")
@AllArgsConstructor
public class SysDictTypeController {
	private final SysDictTypeService sysDictTypeService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:dict:page')")
	public R<PageResult<DictCategoryVO>> page(@ParameterObject @Valid SysDictTypeQuery query) {
//		page
		PageResult<DictCategoryVO> page = sysDictTypeService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * list sql
	 * @param id
	 * @return result
	 */
	@GetMapping("list/sql")
	@Operation(name = "list sql")
	@PreAuthorize("hasAuthority('sys:dict:page')")
	public R<PageResult<SysDictVO.DictData>> listSql(Long id) {
//		get dict sql
		List<SysDictVO.DictData> list = sysDictTypeService.getDictSql(id);
//		page
		PageResult<SysDictVO.DictData> page = new PageResult<>(list, list.size());
//		return
		return R.ok(page);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "get")
	@PreAuthorize("hasAuthority('sys:dict:info')")
	public R<DictCategoryVO> get(@PathVariable("id") Long id) {
//		get dict type
		SysDictTypeEntity entity = sysDictTypeService.getById(id);
//		return
		return R.ok(SysDictTypeConvert.INSTANCE.convert(entity));
	}

	/**
	 * save
	 * @param dictTypeVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:dict:save')")
	public R<String> save(@RequestBody @Valid DictCategoryVO vo) {
//		save
		sysDictTypeService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param dictTypeVO
	 * @return
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:dict:update')")
	public R<String> update(@RequestBody @Valid DictCategoryVO vo) {
//		update
		sysDictTypeService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param idList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:dict:delete')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysDictTypeService.delete(idList);
//		result
		return R.ok();
	}

	/**
	 * all
	 * @return result
	 */
	@GetMapping("all")
	@Operation(name = "all dict")
	public R<List<SysDictVO>> all() {
//		get dict list
		List<SysDictVO> dictList = sysDictTypeService.getDictList();
//		result
		return R.ok(dictList);
	}

	/**
	 * refresh trans cache
	 * @return result
	 */
	@GetMapping("refreshTransCache")
	@Operation(name = "refresh trans cache")
	@PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
	public R<String> refreshTransCache() {
//		refresh trans cache
		sysDictTypeService.refreshTransCache();
//		result
		return R.ok();
	}

}