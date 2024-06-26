package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.vo.SysOrgVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysOrgConvert {
	SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

	SysOrgEntity convert(SysOrgVO vo);

	SysOrgVO convert(SysOrgEntity entity);

	List<SysOrgVO> convertList(List<SysOrgEntity> list);

}
