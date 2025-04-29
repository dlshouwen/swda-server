package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Job;
import com.dlshouwen.swda.bms.system.service.IJobService;
import com.dlshouwen.swda.bms.system.vo.JobVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * job
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/job")
@Tag(name = "job")
@AllArgsConstructor
public class JobController {
	
	/** job service */
	private final IJobService jobService;

	/**
	 * get job page result
	 * @param query
	 * @return job page result
	 */
	@PostMapping("/page")
	@Operation(name = "get job page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:job:page')")
	public R<PageResult<JobVO>> getJobPageResult(@RequestBody @Valid Query<Job> query) {
//		get job page result
		PageResult<JobVO> pageResult = jobService.getJobPageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get job data
	 * @param jobId
	 * @return job data
	 */
	@GetMapping("/{jobId}/data")
	@Operation(name = "get job data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:job:data')")
	public R<JobVO> getJobData(@PathVariable("jobId") Long jobId) {
//		get job data
		JobVO job = jobService.getJobData(jobId);
//		return job
		return R.ok(job);
	}

	/**
	 * add job
	 * @param jobVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add job", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:job:add')")
	public R<String> addJob(@RequestBody @Valid JobVO jobVO) {
//		add job
		jobService.addJob(jobVO);
//		return
		return R.ok();
	}

	/**
	 * update job
	 * @param jobVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update job", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:job:update')")
	public R<String> updateJob(@RequestBody @Valid JobVO jobVO) {
//		update job
		jobService.updateJob(jobVO);
//		return
		return R.ok();
	}

	/**
	 * delete job
	 * @param jobIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:job:delete')")
	public R<String> deleteJob(@RequestBody List<Long> jobIdList) {
//		delete job
		jobService.deleteJob(jobIdList);
//		return
		return R.ok();
	}
	
	/**
	 * pause job
	 * @param jobIdList
	 * @return result
	 */
	@PostMapping("/pause")
	@Operation(name = "pause job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:job:pause')")
	public R<String> pauseJob(@RequestBody List<Long> jobIdList) {
//		pause job
		jobService.pauseJob(jobIdList);
//		return
		return R.ok();
	}
	
	/**
	 * resume job
	 * @param jobIdList
	 * @return result
	 */
	@PostMapping("/resume")
	@Operation(name = "resume job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:job:resume')")
	public R<String> resumeJob(@RequestBody List<Long> jobIdList) {
//		resume job
		jobService.resumeJob(jobIdList);
//		return
		return R.ok();
	}

	/**
	 * run job
	 * @param jobIdList
	 * @return result
	 */
	@PostMapping("/run")
	@Operation(name = "run job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:job:run')")
	public R<String> runJob(@RequestBody List<Long> jobIdList) {
//		run job
		jobService.runJob(jobIdList);
//		return
		return R.ok();
	}

}