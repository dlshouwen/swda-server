package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Organ;
import com.dlshouwen.swda.bms.vo.OrganVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * organ convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface OrganConvert {
	
	/** instance */
	OrganConvert INSTANCE = Mappers.getMapper(OrganConvert.class);

	/**
	 * convert
	 * @param organVO
	 * @return organ
	 */
	Organ convert(OrganVO vo);

	/**
	 * convert to vo
	 * @param organ
	 * @return organ vo
	 */
	OrganVO convert2VO(Organ entity);

	/**
	 * convert to vo list
	 * @param organList
	 * @return organ vo list
	 */
	List<OrganVO> convert2VOList(List<Organ> list);

}
