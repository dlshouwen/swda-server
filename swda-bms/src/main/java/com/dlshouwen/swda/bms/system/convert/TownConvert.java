package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Town;
import com.dlshouwen.swda.bms.system.vo.TownVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * town convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TownConvert {
	
	/** instance */
	TownConvert INSTANCE = Mappers.getMapper(TownConvert.class);

	/**
	 * convert
	 * @param townVO
	 * @return town
	 */
	Town convert(TownVO vo);

	/**
	 * convert to vo
	 * @param town
	 * @return town vo
	 */
	TownVO convert2VO(Town entity);

	/**
	 * convert to vo list
	 * @param townList
	 * @return town vo list
	 */
	List<TownVO> convert2VOList(List<Town> list);

}
