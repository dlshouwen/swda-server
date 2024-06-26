package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.core.entity.auth.UserDetail;

/**
 * user convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserConvert {

	/** instance */
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	/**
	 * convert detail
	 * @param userEntity
	 * @return userDetail
	 */
	UserDetail convertDetail(SysUserEntity entity);

}
