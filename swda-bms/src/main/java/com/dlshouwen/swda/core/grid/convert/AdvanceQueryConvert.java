package com.dlshouwen.swda.bms.core.grid.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.core.grid.entity.AdvanceQuery;
import com.dlshouwen.swda.bms.core.grid.vo.AdvanceQueryVO;

/**
 * advance query convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdvanceQueryConvert {
	
	/** instance */
	AdvanceQueryConvert INSTANCE = Mappers.getMapper(AdvanceQueryConvert.class);
	
	/**
	 * convert
	 * @param advance query vo
	 * @return advance query
	 */
	AdvanceQuery convert(AdvanceQueryVO vo);
	
	/**
	 * convert to vo
	 * @param advance query
	 * @return advance query vo
	 */
	AdvanceQueryVO convert2VO(AdvanceQuery entity);
	
	/**
	 * convert list
	 * @param advance query vo list
	 * @return advance query list
	 */
	List<AdvanceQuery> convertList(List<AdvanceQueryVO> list);
	
	/**
	 * convert to vo list
	 * @param advance query list
	 * @return advance query vo list
	 */
	List<AdvanceQueryVO> convert2VOList(List<AdvanceQuery> list);

}
