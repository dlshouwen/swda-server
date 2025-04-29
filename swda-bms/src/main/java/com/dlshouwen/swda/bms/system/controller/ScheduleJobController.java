package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
import com.dlshouwen.swda.bms.system.service.IScheduleJobService;
import com.dlshouwen.swda.bms.system.vo.ScheduleJobVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * schedule job
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/schedule_job")
@Tag(name = "schedule job")
@AllArgsConstructor
public class ScheduleJobController {
	
	/** schedule job service */
	private final IScheduleJobService scheduleJobService;

	/**
	 * get schedule job page result
	 * @param query
	 * @return schedule job page result
	 */
	@PostMapping("/page")
	@Operation(name = "get schedule job page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:page')")
	public R<PageResult<ScheduleJobVO>> getScheduleJobPageResult(@RequestBody @Valid Query<ScheduleJob> query) {
//		get schedule job page result
		PageResult<ScheduleJobVO> pageResult = scheduleJobService.getScheduleJobPageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get schedule job data
	 * @param scheduleJobId
	 * @return schedule job data
	 */
	@GetMapping("/{scheduleJobId}/data")
	@Operation(name = "get schedule job data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:data')")
	public R<ScheduleJobVO> getScheduleJobData(@PathVariable("scheduleJobId") Long scheduleJobId) {
//		get schedule job data
		ScheduleJobVO scheduleJob = scheduleJobService.getScheduleJobData(scheduleJobId);
//		return schedule job
		return R.ok(scheduleJob);
	}

	/**
	 * add schedule job
	 * @param scheduleJobVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add schedule job", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:add')")
	public R<String> addScheduleJob(@RequestBody @Valid ScheduleJobVO scheduleJobVO) {
//		add schedule job
		scheduleJobService.addScheduleJob(scheduleJobVO);
//		return
		return R.ok();
	}

	/**
	 * update schedule job
	 * @param scheduleJobVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update schedule job", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:update')")
	public R<String> updateScheduleJob(@RequestBody @Valid ScheduleJobVO scheduleJobVO) {
//		update schedule job
		scheduleJobService.updateScheduleJob(scheduleJobVO);
//		return
		return R.ok();
	}

	/**
	 * delete schedule job
	 * @param scheduleJobIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete schedule job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:delete')")
	public R<String> deleteScheduleJob(@RequestBody List<Long> scheduleJobIdList) {
//		delete schedule job
		scheduleJobService.deleteScheduleJob(scheduleJobIdList);
//		return
		return R.ok();
	}
	
	/**
	 * pause schedule job
	 * @param scheduleJobIdList
	 * @return result
	 */
	@PostMapping("/pause")
	@Operation(name = "pause schedule job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:pause')")
	public R<String> pauseScheduleJob(@RequestBody List<Long> scheduleJobIdList) {
//		pause schedule job
		scheduleJobService.pauseScheduleJob(scheduleJobIdList);
//		return
		return R.ok();
	}
	
	/**
	 * resume schedule job
	 * @param scheduleJobIdList
	 * @return result
	 */
	@PostMapping("/resume")
	@Operation(name = "resume schedule job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:resume')")
	public R<String> resumeScheduleJob(@RequestBody List<Long> scheduleJobIdList) {
//		resume schedule job
		scheduleJobService.resumeScheduleJob(scheduleJobIdList);
//		return
		return R.ok();
	}

	/**
	 * run schedule job
	 * @param scheduleJobIdList
	 * @return result
	 */
	@PostMapping("/run")
	@Operation(name = "run schedule job", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:schedule_job:run')")
	public R<String> runScheduleJob(@RequestBody List<Long> scheduleJobIdList) {
//		run schedule job
		scheduleJobService.runScheduleJob(scheduleJobIdList);
//		return
		return R.ok();
	}

}