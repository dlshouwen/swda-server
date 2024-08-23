package com.dlshouwen.swda.core.grid.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.core.grid.entity.AdvanceQuerySort;
import com.dlshouwen.swda.core.grid.vo.AdvanceQuerySortVO;

/**
 * advance query sort convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface AdvanceQuerySortConvert {
	
	/** instance */
	AdvanceQuerySortConvert INSTANCE = Mappers.getMapper(AdvanceQuerySortConvert.class);
	
	/**
	 * convert
	 * @param advance query sort vo
	 * @return advance query sort
	 */
	AdvanceQuerySort convert(AdvanceQuerySortVO vo);
	
	/**
	 * convert to vo
	 * @param advance query sort
	 * @return advance query sort vo
	 */
	AdvanceQuerySortVO convert2VO(AdvanceQuerySort entity);
	
	/**
	 * convert list
	 * @param advance query sort vo list
	 * @return advance query sort list
	 */
	List<AdvanceQuerySort> convertList(List<AdvanceQuerySortVO> list);
	
	/**
	 * convert to vo list
	 * @param advance query sort list
	 * @return advance query sort vo list
	 */
	List<AdvanceQuerySortVO> convert2VOList(List<AdvanceQuerySort> list);

}
