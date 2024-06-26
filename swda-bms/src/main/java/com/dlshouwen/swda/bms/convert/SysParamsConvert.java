package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.bms.vo.SysParamsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 参数管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysParamsConvert {
	SysParamsConvert INSTANCE = Mappers.getMapper(SysParamsConvert.class);

	SysParamsEntity convert(SysParamsVO vo);

	SysParamsVO convert(SysParamsEntity entity);

	List<SysParamsVO> convertList(List<SysParamsEntity> list);

}