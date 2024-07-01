package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.enums.OperateType;
import com.dlshouwen.swda.bms.query.SysLogLoginQuery;
import com.dlshouwen.swda.bms.service.SysLogLoginService;
import com.dlshouwen.swda.bms.vo.LoginLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/login")
@Tag(name = "login log")
@AllArgsConstructor
public class SysLogLoginController {
	
	/** login log service */
	private final SysLogLoginService sysLogLoginService;

	/**
	 * page
	 * @param query
	 * @return result
	 */
	@GetMapping("page")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('sys:log:login')")
	public R<PageResult<LoginLogVO>> page(@ParameterObject @Valid SysLogLoginQuery query) {
//		page
		PageResult<LoginLogVO> page = sysLogLoginService.page(query);
//		return
		return R.ok(page);
	}

	/**
	 * export
	 */
	@GetMapping("export")
	@Operation(name = "export excel", type = OperateType.EXPORT)
	@PreAuthorize("hasAuthority('sys:log:login')")
	public void export() {
//		export
		sysLogLoginService.export();
	}

}