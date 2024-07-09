package com.dlshouwen.swda.core.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.core.dto.DataLogDTO;
import com.dlshouwen.swda.core.entity.log.DataLog;

/**
 * data log convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface DataLogConvert {
	
	/** instance */
	DataLogConvert INSTANCE = Mappers.getMapper(DataLogConvert.class);
	
	/**
	 * convert
	 * @param dataLogDTO
	 * @return data log
	 */
	DataLog convert(DataLogDTO dto);

}