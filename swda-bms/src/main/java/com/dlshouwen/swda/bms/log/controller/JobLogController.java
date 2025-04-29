package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.log.convert.JobLogConvert;
import com.dlshouwen.swda.bms.log.entity.JobLog;
import com.dlshouwen.swda.bms.log.service.IJobLogService;
import com.dlshouwen.swda.bms.log.vo.JobLogVO;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * job log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/job_log")
@Tag(name = "job log")
@AllArgsConstructor
public class JobLogController {
	
	/** job log service */
	private final IJobLogService jobLogService;

	/**
	 * get job log page result
	 * @param query
	 * @return job log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get job log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:job_log:page')")
	public R<PageResult<JobLogVO>> getJobLogPageResult(@RequestBody @Valid Query<JobLog> query) {
//		get job log page result
		PageResult<JobLogVO> pageResult = jobLogService.getJobLogPageResult(query);
//		return page result
		return R.ok(pageResult);
	}

	/**
	 * get job log data
	 * @param jobLogId
	 * @return job log data
	 */
	@GetMapping("/{jobLogId}/data")
	@Operation(name = "get job log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:job_log:data')")
	public R<JobLogVO> getJobLogData(@PathVariable("jobLogId") Long jobLogId) {
//		get job log
		JobLog jobLog = jobLogService.getById(jobLogId);
//		conert to job log vo for return
		return R.ok(JobLogConvert.INSTANCE.convert2VO(jobLog));
	}

	/**
	 * delete job log
	 * @param jobLogIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete job log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:job_log:delete')")
	public R<String> deleteJobLog(@RequestBody List<Long> jobLogIdList) {
//		delete job log
		jobLogService.deleteJobLog(jobLogIdList);
//		return
		return R.ok();
	}

}