package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.System;
import com.dlshouwen.swda.bms.system.vo.SystemVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * system convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemConvert {
	
	/** instance */
	SystemConvert INSTANCE = Mappers.getMapper(SystemConvert.class);

	/**
	 * convert
	 * @param systemVO
	 * @return system
	 */
	System convert(SystemVO vo);

	/**
	 * convert to vo
	 * @param system
	 * @return system vo
	 */
	SystemVO convert2VO(System entity);

	/**
	 * convert to vo list
	 * @param systemList
	 * @return system vo list
	 */
	List<SystemVO> convert2VOList(List<System> list);

}
