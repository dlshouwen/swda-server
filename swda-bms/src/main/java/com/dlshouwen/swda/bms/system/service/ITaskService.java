package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.Task;
import com.dlshouwen.swda.bms.system.vo.TaskVO;

import java.util.List;

/**
 * task service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ITaskService extends BaseService<Task> {

	/**
	 * get task page result
	 * @param query
	 * @return task page result
	 */
	PageResult<TaskVO> getTaskPageResult(Query<Task> query);

	/**
	 * get task data
	 * @param taskId
	 * @return task data
	 */
	TaskVO getTaskData(Long taskId);

	/**
	 * add task
	 * @param taskVO
	 */
	void addTask(TaskVO vo);

	/**
	 * update task
	 * @param taskVO
	 */
	void updateTask(TaskVO vo);

	/**
	 * delete task
	 * @param taskIdList
	 */
	void deleteTask(List<Long> taskIdList);

}
