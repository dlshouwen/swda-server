package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.service.IAttrService;
import com.dlshouwen.swda.bms.system.vo.AttrVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * attr
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/attr")
@Tag(name = "attr")
@AllArgsConstructor
public class AttrController {
	
	/** attr service */
	private final IAttrService attrService;

	/**
	 * get attr list
	 * @return attr list
	 */
	@PostMapping("/list")
	@Operation(name = "get attr list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:attr:list')")
	public R<List<AttrVO>> getAttrList() {
//		get attr list
		List<AttrVO> attrList = attrService.getAttrList();
//		return attr list
		return R.ok(attrList);
	}

	/**
	 * save attr list
	 * @param attrList
	 * @return result
	 */
	@PostMapping("/save")
	@Operation(name = "save attr list", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:attr:save')")
	public R<String> saveAttrList(@RequestBody List<AttrVO> attrList) {
//		save attr list
		attrService.saveAttrList(attrList);
//		return
		return R.ok();
	}

}