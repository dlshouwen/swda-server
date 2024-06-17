package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysOrgEntity;
import com.dlshouwen.swda.auth.vo.SysOrgVO;

import java.util.List;


@Mapper
public interface SysOrgConvert {
    SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

    SysOrgEntity convert(SysOrgVO vo);

    SysOrgVO convert(SysOrgEntity entity);

    List<SysOrgVO> convertList(List<SysOrgEntity> list);

}
