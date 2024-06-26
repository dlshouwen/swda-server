package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.bms.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleConvert {
	SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

	SysRoleVO convert(SysRoleEntity entity);

	SysRoleEntity convert(SysRoleVO vo);

	List<SysRoleVO> convertList(List<SysRoleEntity> list);

}
