package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.vo.DictVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
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
