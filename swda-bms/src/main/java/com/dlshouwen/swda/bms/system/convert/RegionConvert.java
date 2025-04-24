package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Region;
import com.dlshouwen.swda.bms.system.vo.RegionVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * region convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegionConvert {
	
	/** instance */
	RegionConvert INSTANCE = Mappers.getMapper(RegionConvert.class);

	/**
	 * convert
	 * @param regionVO
	 * @return region
	 */
	Region convert(RegionVO vo);

	/**
	 * convert to vo
	 * @param region
	 * @return region vo
	 */
	@Mapping(source = "regionId", target = "id")
	@Mapping(source = "preRegionId", target = "pid")
	@Mapping(source = "regionName", target = "name")
	@Mapping(source = "hasChildren", target = "hasChildren")
	RegionVO convert2VO(Region entity);

	/**
	 * convert to vo list
	 * @param regionList
	 * @return region vo list
	 */
	List<RegionVO> convert2VOList(List<Region> list);

}
