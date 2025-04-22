package com.dlshouwen.swda.bms.permission.convert;

import com.dlshouwen.swda.bms.permission.entity.Organ;
import com.dlshouwen.swda.bms.permission.vo.OrganVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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
	@Mappings({
		@Mapping(source="organId", target="id"),
		@Mapping(source="preOrganId", target="pid"),
		@Mapping(source="organName", target="name"),
		@Mapping(source="organName", target="label"),
		@Mapping(source="organId", target="value")
	})
	OrganVO convert2VO(Organ entity);

	/**
	 * convert to vo list
	 * @param organList
	 * @return organ vo list
	 */
	@Mappings({
		@Mapping(source="organId", target="id"),
		@Mapping(source="preOrganId", target="pid"),
		@Mapping(source="organName", target="name"),
		@Mapping(source="organName", target="label"),
		@Mapping(source="organId", target="value")
	})
	List<OrganVO> convert2VOList(List<Organ> list);

}
