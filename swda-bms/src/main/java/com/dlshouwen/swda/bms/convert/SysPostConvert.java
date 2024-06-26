package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.vo.SysPostVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysPostConvert {
	SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

	SysPostVO convert(SysPostEntity entity);

	SysPostEntity convert(SysPostVO vo);

	List<SysPostVO> convertList(List<SysPostEntity> list);

}
