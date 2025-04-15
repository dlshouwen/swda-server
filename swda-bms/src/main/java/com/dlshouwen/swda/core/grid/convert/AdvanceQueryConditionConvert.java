package com.dlshouwen.swda.core.grid.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.core.grid.entity.AdvanceQueryCondition;
import com.dlshouwen.swda.core.grid.vo.AdvanceQueryConditionVO;

/**
 * advance query condition convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdvanceQueryConditionConvert {
	
	/** instance */
	AdvanceQueryConditionConvert INSTANCE = Mappers.getMapper(AdvanceQueryConditionConvert.class);
	
	/**
	 * convert
	 * @param advance query condition vo
	 * @return advance query condition
	 */
	AdvanceQueryCondition convert(AdvanceQueryConditionVO vo);
	
	/**
	 * convert to vo
	 * @param advance query condition
	 * @return advance query condition vo
	 */
	AdvanceQueryConditionVO convert2VO(AdvanceQueryCondition entity);
	
	/**
	 * convert list
	 * @param advance query condition vo list
	 * @return advance query condition list
	 */
	List<AdvanceQueryCondition> convertList(List<AdvanceQueryConditionVO> list);
	
	/**
	 * convert to vo list
	 * @param advance query condition list
	 * @return advance query condition vo list
	 */
	List<AdvanceQueryConditionVO> convert2VOList(List<AdvanceQueryCondition> list);

}
