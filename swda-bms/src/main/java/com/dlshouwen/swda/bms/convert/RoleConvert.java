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
public interface RoleConvert {
	
	/** instance */
	RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

	/**
	 * convert
	 * @param roleVO
	 * @return role
	 */
	Role convert(RoleVO vo);

	/**
	 * convert to vo
	 * @param role
	 * @return role vo
	 */
	RoleVO convert2VO(Role entity);

	/**
	 * convert to vo list
	 * @param roleList
	 * @return role vo list
	 */
	List<RoleVO> convert2VOList(List<Role> list);

}
