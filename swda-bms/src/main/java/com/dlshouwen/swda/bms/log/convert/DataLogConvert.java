package com.dlshouwen.swda.bms.log.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.log.vo.DataLogVO;
import com.dlshouwen.swda.core.log.dto.DataLogDTO;
import com.dlshouwen.swda.core.log.entity.DataLog;

import java.util.List;

/**
 * data log convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataLogConvert {
	
	/** instance */
	DataLogConvert INSTANCE = Mappers.getMapper(DataLogConvert.class);
	
	/**
	 * convert
	 * @param dataLogDTO
	 * @return data log
	 */
	DataLog convert(DataLogDTO dto);

	/**
	 * convert
	 * @param dataLogVO
	 * @return data log
	 */
	DataLog convert(DataLogVO vo);
	
	/**
	 * convert to vo
	 * @param dataLog
	 * @return data log vo
	 */
	DataLogVO convert2VO(DataLog entity);

	/**
	 * convert to vo list
	 * @param dataLogList
	 * @return data log vo list
	 */
	List<DataLogVO> convert2VOList(List<DataLog> list);

}