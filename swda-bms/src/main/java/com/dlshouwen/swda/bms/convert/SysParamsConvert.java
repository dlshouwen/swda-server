package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.vo.SysParamsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * params convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysParamsConvert {
	
	/** instance */
	SysParamsConvert INSTANCE = Mappers.getMapper(SysParamsConvert.class);

	/**
	 * convert
	 * @param paramsVO
	 * @return params
	 */
	SysParamsEntity convert(SysParamsVO vo);

	/**
	 * convert
	 * @param params
	 * @return params vo
	 */
	SysParamsVO convert(SysParamsEntity entity);

	/**
	 * convert list
	 * @param paramsList
	 * @return params vo list
	 */
	List<SysParamsVO> convertList(List<SysParamsEntity> list);

}