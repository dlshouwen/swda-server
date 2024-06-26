package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.bms.query.SysLogOperateQuery;
import com.dlshouwen.swda.bms.service.SysLogOperateService;
import com.dlshouwen.swda.bms.vo.SysLogOperateVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/operate")
@Tag(name = "operation log")
@AllArgsConstructor
public class SysLogOperateController {
	
	/** operation log service */
	private final SysLogOperateService sysLogOperateService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:operate:all')")
	public R<PageResult<SysLogOperateVO>> page(@ParameterObject @Valid SysLogOperateQuery query) {
//		page
		PageResult<SysLogOperateVO> page = sysLogOperateService.page(query);
//		return
		return R.ok(page);
	}

}