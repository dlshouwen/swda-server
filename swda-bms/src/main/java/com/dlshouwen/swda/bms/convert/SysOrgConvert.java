package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.vo.SysOrgVO;
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
	SysOrgEntity convert(SysOrgVO vo);

	/**
	 * convert
	 * @param organ
	 * @return organ vo
	 */
	SysOrgVO convert(SysOrgEntity entity);

	/**
	 * convert list
	 * @param organList
	 * @return organ vo list
	 */
	List<SysOrgVO> convertList(List<SysOrgEntity> list);

}
