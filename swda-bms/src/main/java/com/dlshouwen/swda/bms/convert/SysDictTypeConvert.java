package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysDictTypeEntity;
import com.dlshouwen.swda.bms.vo.SysDictTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict type convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysDictTypeConvert {
	
	/** instance */
	SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

	/**
	 * convert
	 * @param dictType
	 * @return dict type vo
	 */
	SysDictTypeVO convert(SysDictTypeEntity entity);

	/**
	 * convert
	 * @param dictTypeVO
	 * @return dict type
	 */
	SysDictTypeEntity convert(SysDictTypeVO vo);

	/**
	 * convert list
	 * @param dictTypeList
	 * @return dict type vo list
	 */
	List<SysDictTypeVO> convertList(List<SysDictTypeEntity> list);

}
