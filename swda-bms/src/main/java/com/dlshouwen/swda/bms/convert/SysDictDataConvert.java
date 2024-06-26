package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.vo.SysDictDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysDictDataConvert {
	SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

	SysDictDataVO convert(SysDictDataEntity entity);

	SysDictDataEntity convert(SysDictDataVO vo);

	List<SysDictDataVO> convertList(List<SysDictDataEntity> list);

}
