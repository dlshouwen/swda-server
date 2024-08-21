package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.entity.PageResult;
import com.dlshouwen.swda.core.grid.entity.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.service.IOperationLogService;
import com.dlshouwen.swda.bms.log.vo.OperationLogVO;

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
@RequestMapping("/operation_log")
@Tag(name = "operation log")
@AllArgsConstructor
public class OperateLogController {
	
	/** operation log service */
	private final IOperationLogService operationLogService;

	/**
	 * get operation log list
	 * @param query
	 * @return operation log list
	 */
	@GetMapping("/list")
	@Operation(name = "get operation log list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:operate:list')")
	public R<PageResult<OperationLogVO>> getOperationLogList(@ParameterObject @Valid Query<OperationLog> query) {
//		get operation log list
		PageResult<OperationLogVO> pageResult = operationLogService.getOperationLogList(query);
//		return page result
		return R.ok(pageResult);
	}

}