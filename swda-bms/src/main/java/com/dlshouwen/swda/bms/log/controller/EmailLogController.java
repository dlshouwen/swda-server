package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dlshouwen.swda.bms.log.convert.EmailLogConvert;
import com.dlshouwen.swda.bms.log.entity.EmailLog;
import com.dlshouwen.swda.bms.log.service.IEmailLogService;
import com.dlshouwen.swda.bms.log.vo.EmailLogVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import java.util.List;

/**
 * email log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/email_log")
@Tag(name = "email log")
@AllArgsConstructor
public class EmailLogController {
	
	/** email log service */
	private final IEmailLogService emailLogService;

	/**
	 * get mail log page result	
	 * @param query
	 * @return mail log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get email log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:email_log:page')")
	public R<PageResult<EmailLogVO>> getEmailLogPageResult(@ParameterObject @Valid Query<EmailLog> query) {
//		get email log page result
		PageResult<EmailLogVO> pageResult = emailLogService.getEmailLogPageResult(query);
//		return email log page result
		return R.ok(pageResult);
	}

	/**
	 * get email log data
	 * @param emailLogId
	 * @return email log data
	 */
	@GetMapping("/{emailLogId}/data")
	@Operation(name = "get email log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:email_log:data')")
	public R<EmailLogVO> getEmailLogData(@PathVariable("emailLogId") Long emailLogId) {
//		get email log
		EmailLog emailLog = emailLogService.getById(emailLogId);
//		conert to email log vo for return
		return R.ok(EmailLogConvert.INSTANCE.convert2VO(emailLog));
	}

	/**
	 * delete email log
	 * @param emailLogIdList
	 * @return result
	 */
	@GetMapping("/delete")
	@Operation(name = "delete email log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:email_log:delete')")
	public R<String> deleteEmailLog(@RequestBody List<Long> emailLogIdList) {
//		delete email log
		emailLogService.deleteEmailLog(emailLogIdList);
//		return
		return R.ok();
	}

}