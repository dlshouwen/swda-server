package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.bms.vo.AuthPlatformVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
	 * @return third login config
	 */
	SysThirdLoginConfigEntity convert(AuthPlatformVO vo);

	/**
	 * convert
	 * @param thirdLoginConfig
	 * @return third login config vo
	 */
	AuthPlatformVO convert(SysThirdLoginConfigEntity entity);

	/**
	 * convert list
	 * @param thirdLoginConfigList
	 * @return third login config vo list
	 */
	List<AuthPlatformVO> convertList(List<SysThirdLoginConfigEntity> list);

}