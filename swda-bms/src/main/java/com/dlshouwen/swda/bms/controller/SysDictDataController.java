package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysDictDataConvert;
import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.service.SysDictDataService;
import com.dlshouwen.swda.bms.vo.DictVO;
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
@RequestMapping("sys/dict/data")
@Tag(name = "dict")
@AllArgsConstructor
public class SysDictDataController {
	
	/** dict data service */
	private final SysDictDataService sysDictDataService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:dict:page')")
	public R<PageResult<DictVO>> page(@ParameterObject @Valid SysDictDataQuery query) {
//		page
		PageResult<DictVO> page = sysDictDataService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "data")
	@PreAuthorize("hasAuthority('sys:dict:info')")
	public R<DictVO> get(@PathVariable("id") Long id) {
//		get dict
		Dict entity = sysDictDataService.getById(id);
//		return
		return R.ok(SysDictDataConvert.INSTANCE.convert(entity));
	}

	/**
	 * save
	 * @param dictVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:dict:save')")
	public R<String> save(@RequestBody @Valid DictVO vo) {
//		save
		sysDictDataService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param dictVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:dict:update')")
	public R<String> update(@RequestBody @Valid DictVO vo) {
//		update
		sysDictDataService.update(vo);
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
		sysDictDataService.delete(idList);
//		return
		return R.ok();
	}

}