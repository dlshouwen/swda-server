package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.convert.SysPostConvert;
import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.service.SysPostService;
import com.dlshouwen.swda.bms.vo.SysPostVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * post
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/post")
@Tag(name = "post")
@AllArgsConstructor
public class SysPostController {
	
	/** post service */
	private final SysPostService sysPostService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:post:page')")
	public R<PageResult<SysPostVO>> page(@ParameterObject @Valid SysPostQuery query) {
//		page
		PageResult<SysPostVO> page = sysPostService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * list
	 * @return result
	 */
	@GetMapping("list")
	@Operation(name = "list")
	public R<List<SysPostVO>> list() {
//		get list
		List<SysPostVO> list = sysPostService.getList();
//		return
		return R.ok(list);
	}

	/**
	 * get
	 * @param id
	 * @return result
	 */
	@GetMapping("{id}")
	@Operation(name = "get")
	@PreAuthorize("hasAuthority('sys:post:info')")
	public R<SysPostVO> get(@PathVariable("id") Long id) {
//		get post by id
		SysPostEntity entity = sysPostService.getById(id);
//		return
		return R.ok(SysPostConvert.INSTANCE.convert(entity));
	}

	/**
	 * save
	 * @param postVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:post:save')")
	public R<String> save(@RequestBody SysPostVO vo) {
//		save
		sysPostService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param postVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:post:update')")
	public R<String> update(@RequestBody @Valid SysPostVO vo) {
//		update
		sysPostService.update(vo);
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
	@PreAuthorize("hasAuthority('sys:post:delete')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysPostService.delete(idList);
//		return
		return R.ok();
	}

	/**
	 * get name list
	 * @param idList
	 * @return result
	 */
	@PostMapping("nameList")
	@Operation(name = "get name list")
	public R<List<String>> nameList(@RequestBody List<Long> idList) {
//		get name list
		List<String> list = sysPostService.getNameList(idList);
//		return
		return R.ok(list);
	}

}