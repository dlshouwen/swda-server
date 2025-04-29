package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Job;
import com.dlshouwen.swda.bms.system.vo.JobVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * job convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobConvert {
	
	/** instance */
	JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

	/**
	 * convert
	 * @param job vo
	 * @return job
	 */
	Job convert(JobVO vo);

	/**
	 * convert to vo
	 * @param job
	 * @return job vo
	 */
	JobVO convert2VO(Job entity);

	/**
	 * convert to vo list
	 * @param job list
	 * @return job vo list
	 */
	List<JobVO> convert2VOList(List<Job> list);

}
