package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.annotation.Operation;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.bms.entity.LoginLog;
import com.dlshouwen.swda.bms.service.ILoginLogService;
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
@RequestMapping("bms/log/login_login")
@Tag(name = "login log")
@AllArgsConstructor
public class LoginLogController {
	
	/** login log service */
	private final ILoginLogService loginLogService;

	/**
	 * get login log list
	 * @param query
	 * @return login log list
	 */
	@GetMapping("/list")
	@Operation(name = "page")
	@PreAuthorize("hasAuthority('bms:login_log:list')")
	public R<PageResult<LoginLogVO>> getLoginLogList(@ParameterObject @Valid Query<LoginLog> query) {
//		get login log list
		PageResult<LoginLogVO> pageResult = loginLogService.getLoginLogList(query);
//		return page result
		return R.ok(pageResult);
	}

}