package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.vo.DictVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * dict data convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysDictDataConvert {
	
	/** instance */
	SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

	/**
	 * convert
	 * @param dict
	 * @return dict vo
	 */
	DictVO convert(Dict entity);

	/**
	 * convert
	 * @param dictVO
	 * @return dict
	 */
	Dict convert(DictVO vo);

	/**
	 * convert list
	 * @param dictList
	 * @return dict vo list
	 */
	List<DictVO> convertList(List<Dict> list);

}
