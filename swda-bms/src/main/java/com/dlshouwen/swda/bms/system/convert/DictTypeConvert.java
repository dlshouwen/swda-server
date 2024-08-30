package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.DictType;
import com.dlshouwen.swda.bms.system.vo.DictTypeVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict type convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictTypeConvert {
	
	/** instance */
	DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

	/**
	 * convert
	 * @param dictTypeVO
	 * @return dict type
	 */
	DictType convert(DictTypeVO vo);

	/**
	 * convert to vo
	 * @param dictType
	 * @return dict type vo
	 */
	DictTypeVO convert2VO(DictType entity);

	/**
	 * convert to vo list
	 * @param dictTypeList
	 * @return dict type vo list
	 */
	List<DictTypeVO> convert2VOList(List<DictType> list);

}
