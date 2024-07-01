package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysLogOperateEntity;
import com.dlshouwen.swda.bms.vo.OperationVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * operation log convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysLogOperateConvert {
	
	/** instance */
	SysLogOperateConvert INSTANCE = Mappers.getMapper(SysLogOperateConvert.class);

	/**
	 * convert
	 * @param operationLogVO
	 * @return operation log
	 */
	SysLogOperateEntity convert(OperationVO vo);

	/**
	 * convert
	 * @param operationLog
	 * @return operation log vo
	 */
	OperationVO convert(SysLogOperateEntity entity);

	/**
	 * convert list
	 * @param operationLogList
	 * @return operation log vo list
	 */
	List<OperationVO> convertList(List<SysLogOperateEntity> list);

}