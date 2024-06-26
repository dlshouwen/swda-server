package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysParamsConvert;
import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.query.SysParamsQuery;
import com.dlshouwen.swda.bms.service.SysParamsService;
import com.dlshouwen.swda.bms.vo.SysParamsVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * params
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/params")
@Tag(name = "params")
@AllArgsConstructor
public class SysParamsController {
	
	/** params service */
	private final SysParamsService sysParamsService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:params:all')")
	public R<PageResult<SysParamsVO>> page(@ParameterObject @Valid SysParamsQuery query) {
//		page
		PageResult<SysParamsVO> page = sysParamsService.page(query);
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
	@PreAuthorize("hasAuthority('sys:params:all')")
	public R<SysParamsVO> get(@PathVariable("id") Long id) {
//		get params
		SysParamsEntity entity = sysParamsService.getById(id);
//		return
		return R.ok(SysParamsConvert.INSTANCE.convert(entity));
	}

	/**
	 * save
	 * @param paramsVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:params:all')")
	public R<String> save(@RequestBody SysParamsVO vo) {
//		save
		sysParamsService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param paramsVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:params:all')")
	public R<String> update(@RequestBody @Valid SysParamsVO vo) {
//		update
		sysParamsService.update(vo);
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
	@PreAuthorize("hasAuthority('sys:params:all')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysParamsService.delete(idList);
//		return
		return R.ok();
	}

}