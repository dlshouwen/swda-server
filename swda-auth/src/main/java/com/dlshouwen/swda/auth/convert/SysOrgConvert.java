package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysOrgEntity;
import com.dlshouwen.swda.auth.vo.SysOrgVO;

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
	 * @return organVO
	 */
	SysOrgVO convert(SysOrgEntity entity);

	/**
	 * convert list
	 * @param organList
	 * @return organVOList
	 */
	List<SysOrgVO> convertList(List<SysOrgEntity> list);

}
