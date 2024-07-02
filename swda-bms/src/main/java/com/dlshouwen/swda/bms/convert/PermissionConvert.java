package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.bms.vo.PermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * permission convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
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
	PermissionVO convert(Permission entity);

	/**
	 * convert to vo list
	 * @param permissionList
	 * @return permission vo list
	 */
	List<PermissionVO> convert2VOList(List<Permission> list);

}
