package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.convert.OrganConvert;
import com.dlshouwen.swda.bms.entity.Organ;
import com.dlshouwen.swda.bms.service.SysOrgService;
import com.dlshouwen.swda.bms.vo.OrganVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * organ
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/org")
@Tag(name = "organ")
@AllArgsConstructor
public class SysOrgController {
	
	/** organ service */
	private final SysOrgService sysOrgService;

	/**
	 * list
	 * @return result
	 */
	@GetMapping("list")
	@Operation(name = "list")
	@PreAuthorize("hasAuthority('sys:org:list')")
	public R<List<OrganVO>> list() {
//		get list
		List<OrganVO> list = sysOrgService.getList();
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
	@PreAuthorize("hasAuthority('sys:org:info')")
	public R<OrganVO> get(@PathVariable("id") Long id) {
//		get organ
		Organ entity = sysOrgService.getById(id);
//		convert to organ vo
		OrganVO vo = OrganConvert.INSTANCE.convert(entity);
//		if has parent
		if (entity.getPid() != null) {
//			get parent organ
			Organ parentEntity = sysOrgService.getById(entity.getPid());
//			set parent name
			vo.setParentName(parentEntity.getName());
		}
//		return
		return R.ok(vo);
	}

	/**
	 * save
	 * @param organVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('sys:org:save')")
	public R<String> save(@RequestBody @Valid OrganVO vo) {
//		save
		sysOrgService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param organVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('sys:org:update')")
	public R<String> update(@RequestBody @Valid OrganVO vo) {
//		update
		sysOrgService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param id
	 * @return result
	 */
	@DeleteMapping("{id}")
	@Operation(name = "delete", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('sys:org:delete')")
	public R<String> delete(@PathVariable("id") Long id) {
//		delete
		sysOrgService.delete(id);
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
		List<String> list = sysOrgService.getNameList(idList);
//		return
		return R.ok(list);
	}

}