package com.dlshouwen.swda.bms.log.convert;

import com.dlshouwen.swda.bms.log.entity.ScheduleJobLog;
import com.dlshouwen.swda.bms.log.vo.ScheduleJobLogVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * schedule job log convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleJobLogConvert {
	
	/** instance */
	ScheduleJobLogConvert INSTANCE = Mappers.getMapper(ScheduleJobLogConvert.class);

	/**
	 * convert
	 * @param scheduleJobLogVO
	 * @return schedule job log
	 */
	ScheduleJobLog convert(ScheduleJobLogVO vo);
	
	/**
	 * convert to vo
	 * @param scheduleJobLog
	 * @return schedule job log vo
	 */
	ScheduleJobLogVO convert2VO(ScheduleJobLog entity);

	/**
	 * convert to vo list
	 * @param scheduleJobLogList
	 * @return schedule job log vo list
	 */
	List<ScheduleJobLogVO> convert2VOList(List<ScheduleJobLog> list);

}