package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.convert.DataLogConvert;
import com.dlshouwen.swda.bms.log.service.IDataLogService;
import com.dlshouwen.swda.bms.log.vo.DataLogVO;

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
 * data log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/data_log")
@Tag(name = "data log")
@AllArgsConstructor
public class DataLogController {
	
	/** data log service */
	private final IDataLogService dataLogService;

	/**
	 * get data log page result
	 * @param query
	 * @return data log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get data log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:data_log:page')")
	public R<PageResult<DataLogVO>> getDataLogPageResult(@ParameterObject @Valid Query<DataLog> query) {
//		get data log list
		PageResult<DataLogVO> pageResult = dataLogService.getDataLogPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get data log data
	 * @param dataLogId
	 * @return data log data
	 */
	@GetMapping("/{dataLogId}/data")
	@Operation(name = "get data log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:data_log:data')")
	public R<DataLogVO> getDataLogData(@PathVariable("dataLogId") Long dataLogId) {
//		get data log
		DataLog dataLog = dataLogService.getById(dataLogId);
//		conert to data log vo for return
		return R.ok(DataLogConvert.INSTANCE.convert2VO(dataLog));
	}

	/**
	 * delete data log
	 * @param dataLogIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete data log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:data_log:delete')")
	public R<String> deleteDataLog(@RequestBody List<Long> dataLogIdList) {
//		delete data log
		dataLogService.deleteDataLog(dataLogIdList);
//		return
		return R.ok();
	}

}