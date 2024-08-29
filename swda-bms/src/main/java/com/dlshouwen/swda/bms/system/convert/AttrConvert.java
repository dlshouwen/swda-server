package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Attr;
import com.dlshouwen.swda.bms.system.vo.AttrVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * attr convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttrConvert {
	
	/** instance */
	AttrConvert INSTANCE = Mappers.getMapper(AttrConvert.class);

	/**
	 * convert
	 * @param attrVO
	 * @return attr
	 */
	Attr convert(AttrVO vo);

	/**
	 * convert list
	 * @param attrList
	 * @return attr vo list
	 */
	List<Attr> convertList(List<AttrVO> list);

	/**
	 * convert to vo
	 * @param attr
	 * @return attr vo
	 */
	AttrVO convert2VO(Attr entity);

	/**
	 * convert to vo list
	 * @param attrList
	 * @return attr vo list
	 */
	List<AttrVO> convert2VOList(List<Attr> list);

}