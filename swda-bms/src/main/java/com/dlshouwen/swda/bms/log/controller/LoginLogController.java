package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.entity.LoginLog;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.convert.LoginLogConvert;
import com.dlshouwen.swda.bms.log.service.ILoginLogService;
import com.dlshouwen.swda.bms.log.vo.LoginLogVO;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/login_log")
@Tag(name = "login log")
@AllArgsConstructor
public class LoginLogController {
	
	/** login log service */
	private final ILoginLogService loginLogService;

	/**
	 * get login log page result
	 * @param query
	 * @return login log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get login log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:login_log:page')")
	public R<PageResult<LoginLogVO>> getLoginLogPageResult(@ParameterObject @Valid Query<LoginLog> query) {
//		get login log page result
		PageResult<LoginLogVO> pageResult = loginLogService.getLoginLogPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get login log data
	 * @param loginLogId
	 * @return login log data
	 */
	@GetMapping("/{loginLogId}/data")
	@Operation(name = "get login log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:login_log:data')")
	public R<LoginLogVO> getLoginLogData(@PathVariable("loginLogId") Long loginLogId) {
//		get login log
		LoginLog loginLog = loginLogService.getById(loginLogId);
//		conert to login log vo for return
		return R.ok(LoginLogConvert.INSTANCE.convert2VO(loginLog));
	}

	/**
	 * delete login log
	 * @param loginLogIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete login log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:login_log:delete')")
	public R<String> deleteLoginLog(@RequestBody List<Long> loginLogIdList) {
//		delete login log
		loginLogService.deleteLoginLog(loginLogIdList);
//		return
		return R.ok();
	}

}