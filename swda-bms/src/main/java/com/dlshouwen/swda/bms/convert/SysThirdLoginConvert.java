package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysThirdLoginEntity;
import com.dlshouwen.swda.bms.vo.SysThirdLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * third login convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysThirdLoginConvert {
	
	/** instance */
	SysThirdLoginConvert INSTANCE = Mappers.getMapper(SysThirdLoginConvert.class);

	/**
	 * convert
	 * @param thirdLoginVO
	 * @return third login
	 */
	SysThirdLoginEntity convert(SysThirdLoginVO vo);

	/**
	 * convert
	 * @param thirdLogin
	 * @return third login vo
	 */
	SysThirdLoginVO convert(SysThirdLoginEntity entity);

	/**
	 * convert list
	 * @param thirdLoginList
	 * @return third login vo list
	 */
	List<SysThirdLoginVO> convertList(List<SysThirdLoginEntity> list);

}