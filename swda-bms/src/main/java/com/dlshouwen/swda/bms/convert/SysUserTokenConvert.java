package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysUserTokenEntity;
import com.dlshouwen.swda.bms.vo.SysUserTokenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户Token
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserTokenConvert {
	SysUserTokenConvert INSTANCE = Mappers.getMapper(SysUserTokenConvert.class);

	SysUserTokenEntity convert(SysUserTokenVO vo);

	SysUserTokenVO convert(SysUserTokenEntity entity);

}