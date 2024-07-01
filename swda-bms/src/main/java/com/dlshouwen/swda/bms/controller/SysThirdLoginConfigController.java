package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.convert.SysThirdLoginConfigConvert;
import com.dlshouwen.swda.bms.entity.AuthPlatform;
import com.dlshouwen.swda.bms.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.bms.vo.AuthPlatformVO;
import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.query.Query;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * third login config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/third/config")
@Tag(name = "third login config")
@AllArgsConstructor
public class SysThirdLoginConfigController {
	
	/** third login config service */
	private final SysThirdLoginConfigService sysThirdLoginConfigService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('third:config:all')")
	public R<PageResult<AuthPlatformVO>> page(@ParameterObject @Valid Query query) {
//		page
		PageResult<AuthPlatformVO> page = sysThirdLoginConfigService.page(query);
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
	@PreAuthorize("hasAuthority('third:config:all')")
	public R<AuthPlatformVO> get(@PathVariable("id") Long id) {
//		get third login config
		AuthPlatform entity = sysThirdLoginConfigService.getById(id);
//		result
		return R.ok(SysThirdLoginConfigConvert.INSTANCE.convert(entity));
	}

	/**
	 * save
	 * @param thirdLoginConfigVO
	 * @return result
	 */
	@PostMapping
	@Operation(name = "save")
	@PreAuthorize("hasAuthority('third:config:all')")
	public R<String> save(@RequestBody AuthPlatformVO vo) {
//		save
		sysThirdLoginConfigService.save(vo);
//		return
		return R.ok();
	}

	/**
	 * update
	 * @param thirdLoginConfigVO
	 * @return result
	 */
	@PutMapping
	@Operation(name = "update")
	@PreAuthorize("hasAuthority('third:config:all')")
	public R<String> update(@RequestBody @Valid AuthPlatformVO vo) {
//		update
		sysThirdLoginConfigService.update(vo);
//		return
		return R.ok();
	}

	/**
	 * delete
	 * @param idList
	 * @return result
	 */
	@DeleteMapping
	@Operation(name = "delete")
	@PreAuthorize("hasAuthority('third:config:all')")
	public R<String> delete(@RequestBody List<Long> idList) {
//		delete
		sysThirdLoginConfigService.delete(idList);
//		return
		return R.ok();
	}

}