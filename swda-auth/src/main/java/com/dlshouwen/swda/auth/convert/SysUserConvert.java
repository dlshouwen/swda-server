package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysUserEntity;
import com.dlshouwen.swda.core.entity.auth.UserDetail;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    UserDetail convertDetail(SysUserEntity entity);

}
