package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.vo.AttrVO;
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
	SysParamsEntity convert(AttrVO vo);

	/**
	 * convert
	 * @param params
	 * @return params vo
	 */
	AttrVO convert(SysParamsEntity entity);

	/**
	 * convert list
	 * @param paramsList
	 * @return params vo list
	 */
	List<AttrVO> convertList(List<SysParamsEntity> list);

}