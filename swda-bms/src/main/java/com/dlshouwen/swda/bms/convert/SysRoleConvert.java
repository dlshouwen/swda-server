package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.vo.RoleVO;
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
	RoleVO convert(Role entity);

	/**
	 * convert
	 * @param roleVO
	 * @return role
	 */
	Role convert(RoleVO vo);

	/**
	 * convert list
	 * @param roleList
	 * @return role vo list
	 */
	List<RoleVO> convertList(List<Role> list);

}
