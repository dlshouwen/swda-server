package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.bms.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * role convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleConvert {
	
	/** instance */
	SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

	/**
	 * convert
	 * @param role
	 * @return role vo
	 */
	SysRoleVO convert(SysRoleEntity entity);

	/**
	 * convert
	 * @param roleVO
	 * @return role
	 */
	SysRoleEntity convert(SysRoleVO vo);

	/**
	 * convert list
	 * @param roleList
	 * @return role vo list
	 */
	List<SysRoleVO> convertList(List<SysRoleEntity> list);

}
