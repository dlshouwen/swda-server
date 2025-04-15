package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.TaskConvert;
import com.dlshouwen.swda.bms.system.entity.Task;
import com.dlshouwen.swda.bms.system.mapper.TaskMapper;
import com.dlshouwen.swda.bms.system.service.ITaskService;
import com.dlshouwen.swda.bms.system.vo.TaskVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * task service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, Task> implements ITaskService {

	/**
	 * get task page result
	 * @param query
	 * @return task page result
	 */
	@Override
	public PageResult<TaskVO> getTaskPageResult(Query<Task> query) {
//		query page
		IPage<Task> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(TaskConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get task data
	 * @param taskId
	 * @return task data
	 */
	@Override
	public TaskVO getTaskData(Long taskId) {
//		get task data
		Task task = this.getById(taskId);
//		convert to task vo for return
		return TaskConvert.INSTANCE.convert2VO(task);
	}

	/**
	 * add task
	 * @param taskVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addTask(TaskVO taskVO) {
//		convert to task
		Task task = TaskConvert.INSTANCE.convert(taskVO);
//		insert task
		this.save(task);
	}

	/**
	 * update task
	 * @param taskVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTask(TaskVO taskVO) {
//		convert to task
		Task task = TaskConvert.INSTANCE.convert(taskVO);
//		update task
		this.updateById(task);
	}

	/**
	 * delete task
	 * @param taskIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTask(List<Long> taskIdList) {
//		dlete task list
		this.removeByIds(taskIdList);
	}

}