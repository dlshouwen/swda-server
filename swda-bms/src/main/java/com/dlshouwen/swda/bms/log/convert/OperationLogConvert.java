package com.dlshouwen.swda.bms.log.convert;

import com.dlshouwen.swda.bms.log.vo.OperationLogVO;
import com.dlshouwen.swda.core.log.entity.OperationLog;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
	 * @param operationLogVO
	 * @return operation log
	 */
	OperationLog convert(OperationLogVO vo);
	
	/**
	 * convert to vo
	 * @param operationLog
	 * @return operation log vo
	 */
	OperationLogVO convert2VO(OperationLog entity);

	/**
	 * convert to vo list
	 * @param operationLogList
	 * @return operation log vo list
	 */
	List<OperationLogVO> convert2VOList(List<OperationLog> list);

}