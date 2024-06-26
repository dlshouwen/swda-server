package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.auth.vo.SysThirdLoginConfigVO;

import java.util.List;

/**
 * third login config convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysThirdLoginConfigConvert {
	
	/** instance */
	SysThirdLoginConfigConvert INSTANCE = Mappers.getMapper(SysThirdLoginConfigConvert.class);

	/**
	 * convert
	 * @param thirdLoginConfigVO
	 * @return thirdLoginConfig
	 */
	SysThirdLoginConfigEntity convert(SysThirdLoginConfigVO vo);

	/**
	 * convert
	 * @param thirdLoginConfig
	 * @return thirdLoginConfigVO
	 */
	SysThirdLoginConfigVO convert(SysThirdLoginConfigEntity entity);

	/**
	 * convert list
	 * @param thirdLoginConfigList
	 * @return thirdLoginConfigVOList
	 */
	List<SysThirdLoginConfigVO> convertList(List<SysThirdLoginConfigEntity> list);

}