package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.convert.OperationLogConvert;
import com.dlshouwen.swda.bms.log.service.IOperationLogService;
import com.dlshouwen.swda.bms.log.vo.OperationLogVO;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/operation_log")
@Tag(name = "operation log")
@AllArgsConstructor
public class OperateLogController {
	
	/** operation log service */
	private final IOperationLogService operationLogService;

	/**
	 * get operation log page result
	 * @param query
	 * @return operation log page result
	 */
	@GetMapping("/page")
	@Operation(name = "get operation log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:operate_log:page')")
	public R<PageResult<OperationLogVO>> getOperationLogPageResult(@ParameterObject @Valid Query<OperationLog> query) {
//		get operation log page result
		PageResult<OperationLogVO> pageResult = operationLogService.getOperationLogPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get operation log data
	 * @param operationLogId
	 * @return operation log data
	 */
	@GetMapping("/data/{id}")
	@Operation(name = "get operation log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:operation_log:data')")
	public R<OperationLogVO> getOperationLogData(@PathVariable("operationLogId") Long operationLogId) {
//		get operation log
		OperationLog operationLog = operationLogService.getById(operationLogId);
//		conert to operation log vo for return
		return R.ok(OperationLogConvert.INSTANCE.convert2VO(operationLog));
	}

	/**
	 * delete operation log
	 * @param operationLogIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete operation log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:operation_log:delete')")
	public R<String> deleteOperationLog(@RequestBody List<Long> operationLogIdList) {
//		delete operation log
		operationLogService.deleteOperationLog(operationLogIdList);
//		return
		return R.ok();
	}

}