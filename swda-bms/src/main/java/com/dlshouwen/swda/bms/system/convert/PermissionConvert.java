package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Permission;
import com.dlshouwen.swda.bms.system.vo.PermissionVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * permission convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionConvert {
	
	/** instance */
	PermissionConvert INSTANCE = Mappers.getMapper(PermissionConvert.class);

	/**
	 * convert
	 * @param permissionVO
	 * @return permission
	 */
	Permission convert(PermissionVO vo);

	/**
	 * convert to vo
	 * @param permission
	 * @return permission vo
	 */
	PermissionVO convert2VO(Permission entity);

	/**
	 * convert to vo list
	 * @param permissionList
	 * @return permission vo list
	 */
	List<PermissionVO> convert2VOList(List<Permission> list);

}
