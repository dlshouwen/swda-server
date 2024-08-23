package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.service.IDataLogService;
import com.dlshouwen.swda.bms.log.vo.DataLogVO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * data log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
@RequestMapping("bms/log/data_log")
@Tag(name = "data log")
@AllArgsConstructor
public class DataLogController {
	
	/** data log service */
	private final IDataLogService dataLogService;

	/**
	 * get data log list
	 * @param query
	 * @return data log list
	 */
	@GetMapping("/list")
	@Operation(name = "get data log list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:data_log:list')")
	public R<PageResult<DataLogVO>> getDataLogList(@ParameterObject @Valid Query<DataLog> query) {
//		get data log list
		PageResult<DataLogVO> pageResult = dataLogService.getDataLogList(query);
//		return page result
		return R.ok(pageResult);
	}

}