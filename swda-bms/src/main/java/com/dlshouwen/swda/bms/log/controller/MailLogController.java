package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.convert.SysMailLogConvert;
import net.maku.system.entity.SysMailLogEntity;
import net.maku.system.query.SysMailLogQuery;
import net.maku.system.service.SysMailLogService;
import net.maku.system.vo.SysMailLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮件日志
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("sys/mail/log")
@Tag(name = "邮件日志")
@AllArgsConstructor
public class MailLogController {
	private final IMailLogService sysMailLogService;

	@GetMapping("page")
	@Operation(summary = "分页")
	@PreAuthorize("hasAuthority('sys:mail:log')")
	public Result<PageResult<MailLogVO>> page(@ParameterObject @Valid SysMailLogQuery query) {
		PageResult<MailLogVO> page = sysMailLogService.page(query);

		return Result.ok(page);
	}

	@GetMapping("{id}")
	@Operation(summary = "信息")
	@PreAuthorize("hasAuthority('sys:mail:log')")
	public Result<MailLogVO> get(@PathVariable("id") Long id) {
		MailLog entity = sysMailLogService.getById(id);

		return Result.ok(MailLogConvert.INSTANCE.convert(entity));
	}

	@DeleteMapping
	@Operation(summary = "删除")
	@PreAuthorize("hasAuthority('sys:mail:log')")
	public Result<String> delete(@RequestBody List<Long> idList) {
		sysMailLogService.delete(idList);

		return Result.ok();
	}
}