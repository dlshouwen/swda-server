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
public interface SysOrgConvert {
	
	/** instance */
	SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

	/**
	 * convert
	 * @param organVO
	 * @return organ
	 */
	Organ convert(OrganVO vo);

	/**
	 * convert
	 * @param organ
	 * @return organ vo
	 */
	OrganVO convert(Organ entity);

	/**
	 * convert list
	 * @param organList
	 * @return organ vo list
	 */
	List<OrganVO> convertList(List<Organ> list);

}
