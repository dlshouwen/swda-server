package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictConvert {
	
	/** instance */
	DictConvert INSTANCE = Mappers.getMapper(DictConvert.class);

	/**
	 * convert
	 * @param dictVO
	 * @return dict
	 */
	Dict convert(DictVO vo);

	/**
	 * convert to vo
	 * @param dict
	 * @return dict vo
	 */
	DictVO convert2VO(Dict entity);

	/**
	 * convert to vo list
	 * @param dictList
	 * @return dict vo list
	 */
	List<DictVO> convert2VOList(List<Dict> list);

}
