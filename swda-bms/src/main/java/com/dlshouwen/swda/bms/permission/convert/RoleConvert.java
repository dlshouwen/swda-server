package com.dlshouwen.swda.bms.permission.convert;

import com.dlshouwen.swda.bms.permission.entity.Role;
import com.dlshouwen.swda.bms.permission.vo.RoleVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * role convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
