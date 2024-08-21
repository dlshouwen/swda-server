package com.dlshouwen.swda.core.log.convert;

import com.dlshouwen.swda.core.log.dto.OperationLogDTO;
import com.dlshouwen.swda.core.log.entity.OperationLog;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * operation log convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface OperationLogConvert {
	
	/** instance */
	OperationLogConvert INSTANCE = Mappers.getMapper(OperationLogConvert.class);

	/**
	 * convert
	 * @param operationLogDTO
	 * @return operation log
	 */
	OperationLog convert(OperationLogDTO dto);

}