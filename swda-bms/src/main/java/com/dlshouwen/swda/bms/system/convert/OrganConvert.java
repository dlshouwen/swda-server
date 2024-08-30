package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Organ;
import com.dlshouwen.swda.bms.system.vo.OrganVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * organ convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
