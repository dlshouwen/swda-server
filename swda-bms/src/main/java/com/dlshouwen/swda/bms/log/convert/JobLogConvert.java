package com.dlshouwen.swda.bms.log.convert;

import com.dlshouwen.swda.bms.log.entity.JobLog;
import com.dlshouwen.swda.bms.log.vo.JobLogVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * job log convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobLogConvert {
	
	/** instance */
	JobLogConvert INSTANCE = Mappers.getMapper(JobLogConvert.class);

	/**
	 * convert
	 * @param jobLogVO
	 * @return job log
	 */
	JobLog convert(JobLogVO vo);
	
	/**
	 * convert to vo
	 * @param jobLog
	 * @return job log vo
	 */
	JobLogVO convert2VO(JobLog entity);

	/**
	 * convert to vo list
	 * @param jobLogList
	 * @return job log vo list
	 */
	List<JobLogVO> convert2VOList(List<JobLog> list);

}