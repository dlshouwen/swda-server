package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.ScheduleJob;
import com.dlshouwen.swda.bms.system.vo.ScheduleJobVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * schedule job convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleJobConvert {
	
	/** instance */
	ScheduleJobConvert INSTANCE = Mappers.getMapper(ScheduleJobConvert.class);

	/**
	 * convert
	 * @param schedule job vo
	 * @return schedule job
	 */
	ScheduleJob convert(ScheduleJobVO vo);

	/**
	 * convert to vo
	 * @param schedule job
	 * @return schedule job vo
	 */
	ScheduleJobVO convert2VO(ScheduleJob entity);

	/**
	 * convert to vo list
	 * @param schedule job list
	 * @return schedule job vo list
	 */
	List<ScheduleJobVO> convert2VOList(List<ScheduleJob> list);

}
