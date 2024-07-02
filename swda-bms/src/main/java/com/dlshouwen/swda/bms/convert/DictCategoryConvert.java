package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.DictCategory;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict category convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface DictCategoryConvert {
	
	/** instance */
	DictCategoryConvert INSTANCE = Mappers.getMapper(DictCategoryConvert.class);

	/**
	 * convert
	 * @param dictCategoryVO
	 * @return dict category
	 */
	DictCategory convert(DictCategoryVO vo);

	/**
	 * convert to vo
	 * @param dictCategory
	 * @return dict category vo
	 */
	DictCategoryVO convert2VO(DictCategory entity);

	/**
	 * convert to vo list
	 * @param dictCategoryList
	 * @return dict category vo list
	 */
	List<DictCategoryVO> convert2VOList(List<DictCategory> list);

}
