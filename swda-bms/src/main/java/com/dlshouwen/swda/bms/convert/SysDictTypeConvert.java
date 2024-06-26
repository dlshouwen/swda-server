package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysDictTypeEntity;
import com.dlshouwen.swda.bms.vo.SysDictTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysDictTypeConvert {
	SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

	SysDictTypeVO convert(SysDictTypeEntity entity);

	SysDictTypeEntity convert(SysDictTypeVO vo);

	List<SysDictTypeVO> convertList(List<SysDictTypeEntity> list);

}
