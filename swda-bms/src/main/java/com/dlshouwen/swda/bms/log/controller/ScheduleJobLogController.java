package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.convert.ScheduleJobLogConvert;
import com.dlshouwen.swda.bms.log.entity.ScheduleJobLog;
import com.dlshouwen.swda.bms.log.service.IScheduleJobLogService;
import com.dlshouwen.swda.bms.log.vo.ScheduleJobLogVO;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * schedule job log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/schedule_job_log")
@Tag(name = "schedule job log")
@AllArgsConstructor
public class ScheduleJobLogController {
	
	/** schedule job log service */
	private final IScheduleJobLogService scheduleJobLogService;

	/**
	 * get schedule job log page result
	 * @param query
	 * @return schedule job log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get schedule job log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:schedule_job_log:page')")
	public R<PageResult<ScheduleJobLogVO>> getScheduleJobLogPageResult(@RequestBody @Valid Query<ScheduleJobLog> query) {
//		get schedule job log page result
		PageResult<ScheduleJobLogVO> pageResult = scheduleJobLogService.getScheduleJobLogPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get schedule job log data
	 * @param scheduleJobLogId
	 * @return schedule job log data
	 */
	@GetMapping("/{scheduleJobLogId}/data")
	@Operation(name = "get schedule job log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:schedule_job_log:data')")
	public R<ScheduleJobLogVO> getScheduleJobLogData(@PathVariable("scheduleJobLogId") Long scheduleJobLogId) {
//		get schedule job log
		ScheduleJobLog scheduleJobLog = scheduleJobLogService.getById(scheduleJobLogId);
//		conert to schedule job log vo for return
		return R.ok(ScheduleJobLogConvert.INSTANCE.convert2VO(scheduleJobLog));
	}

	/**
	 * delete schedule job log
	 * @param scheduleJobLogIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete schedule job log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:schedule_job_log:delete')")
	public R<String> deleteScheduleJobLog(@RequestBody List<Long> scheduleJobLogIdList) {
//		delete schedule job log
		scheduleJobLogService.deleteScheduleJobLog(scheduleJobLogIdList);
//		return
		return R.ok();
	}

}