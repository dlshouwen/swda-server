package com.dlshouwen.swda.core.convert;

import com.dlshouwen.swda.core.dto.OperationLogDTO;
import com.dlshouwen.swda.core.entity.log.OperationLog;

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