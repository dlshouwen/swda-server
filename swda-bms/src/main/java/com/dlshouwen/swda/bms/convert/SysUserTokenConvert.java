package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysUserTokenEntity;
import com.dlshouwen.swda.bms.vo.SysUserTokenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * user token convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserTokenConvert {
	
	/** instance */
	SysUserTokenConvert INSTANCE = Mappers.getMapper(SysUserTokenConvert.class);

	/**
	 * convert
	 * @param userTokenVO
	 * @return user token
	 */
	SysUserTokenEntity convert(SysUserTokenVO vo);

	/**
	 * convert
	 * @param userToken
	 * @return user token vo
	 */
	SysUserTokenVO convert(SysUserTokenEntity entity);

}