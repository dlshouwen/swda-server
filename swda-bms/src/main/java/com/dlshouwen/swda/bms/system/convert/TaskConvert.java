package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Task;
import com.dlshouwen.swda.bms.system.vo.TaskVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * task convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskConvert {
	
	/** instance */
	TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);

	/**
	 * convert
	 * @param taskVO
	 * @return task
	 */
	Task convert(TaskVO vo);

	/**
	 * convert to vo
	 * @param task
	 * @return task vo
	 */
	TaskVO convert2VO(Task entity);

	/**
	 * convert to vo list
	 * @param taskList
	 * @return task vo list
	 */
	List<TaskVO> convert2VOList(List<Task> list);

}
