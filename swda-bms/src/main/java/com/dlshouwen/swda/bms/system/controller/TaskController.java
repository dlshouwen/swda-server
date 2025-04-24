package com.dlshouwen.swda.bms.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.entity.Task;
import com.dlshouwen.swda.bms.system.service.ITaskService;
import com.dlshouwen.swda.bms.system.vo.TaskVO;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * task
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/system/task")
@Tag(name = "task")
@AllArgsConstructor
public class TaskController {
	
	/** task service */
	private final ITaskService taskService;

	/**
	 * get task page result
	 * @param query
	 * @return task page result
	 */
	@PostMapping("/page")
	@Operation(name = "get task page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:task:page')")
	public R<PageResult<TaskVO>> getTaskPageResult(@RequestBody @Valid Query<Task> query) {
//		get task page result
		PageResult<TaskVO> pageResult = taskService.getTaskPageResult(query);
//		return
		return R.ok(pageResult);
	}

	/**
	 * get task data
	 * @param taskId
	 * @return task data
	 */
	@GetMapping("/{taskId}/data")
	@Operation(name = "get task data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:system:task:data')")
	public R<TaskVO> getTaskData(@PathVariable("taskId") Long taskId) {
//		get task data
		TaskVO task = taskService.getTaskData(taskId);
//		return task
		return R.ok(task);
	}

	/**
	 * add task
	 * @param taskVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add task", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:system:task:add')")
	public R<String> addTask(@RequestBody @Valid TaskVO taskVO) {
//		add task
		taskService.addTask(taskVO);
//		return
		return R.ok();
	}

	/**
	 * update task
	 * @param taskVO
	 * @return result
	 */
	@PostMapping("/update")
	@Operation(name = "update task", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:system:task:update')")
	public R<String> updateTask(@RequestBody @Valid TaskVO taskVO) {
//		update task
		taskService.updateTask(taskVO);
//		return
		return R.ok();
	}
	
	/**
	 * open task
	 * @param taskIdList
	 * @return result
	 */
	@PostMapping("/open")
	@Operation(name = "open task", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:task:open')")
	public R<String> openTask(@RequestBody List<Long> taskIdList) {
//		open task
		taskService.openTask(taskIdList);
//		return
		return R.ok();
	}
	
	/**
	 * pause task
	 * @param taskIdList
	 * @return result
	 */
	@PostMapping("/pause")
	@Operation(name = "pause task", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:task:pause')")
	public R<String> pauseTask(@RequestBody List<Long> taskIdList) {
//		pause task
		taskService.pauseTask(taskIdList);
//		return
		return R.ok();
	}

	/**
	 * execute task
	 * @param taskIdList
	 * @return result
	 */
	@PostMapping("/execute")
	@Operation(name = "execute task", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:task:execute')")
	public R<String> executeTask(@RequestBody List<Long> taskIdList) {
//		execute task
		taskService.executeTask(taskIdList);
//		return
		return R.ok();
	}

	/**
	 * delete task
	 * @param taskIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete task", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:system:task:delete')")
	public R<String> deleteTask(@RequestBody List<Long> taskIdList) {
//		delete task
		taskService.deleteTask(taskIdList);
//		return
		return R.ok();
	}

}