package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.convert.SysSmsLogConvert;
import net.maku.system.entity.SysSmsLogEntity;
import net.maku.system.query.SysSmsLogQuery;
import net.maku.system.service.SysSmsLogService;
import net.maku.system.vo.SysSmsLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信日志
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("sys/sms/log")
@Tag(name = "短信日志")
@AllArgsConstructor
public class SmsLogController {
	private final SysSmsLogService sysSmsLogService;

	@GetMapping("page")
	@Operation(summary = "分页")
	@PreAuthorize("hasAuthority('sys:sms:log')")
	public Result<PageResult<SmsLogVO>> page(@ParameterObject @Valid SysSmsLogQuery query) {
		PageResult<SmsLogVO> page = sysSmsLogService.page(query);

		return Result.ok(page);
	}

	@GetMapping("{id}")
	@Operation(summary = "信息")
	@PreAuthorize("hasAuthority('sys:sms:log')")
	public Result<SmsLogVO> get(@PathVariable("id") Long id) {
		SysSmsLogEntity entity = sysSmsLogService.getById(id);

		return Result.ok(SmsLogConvert.INSTANCE.convert(entity));
	}

}