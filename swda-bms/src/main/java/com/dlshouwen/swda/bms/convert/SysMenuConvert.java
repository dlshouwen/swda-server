package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.bms.vo.PermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * menu convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysMenuConvert {
	
	/** instance */
	SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

	/**
	 * convert
	 * @param menuVO
	 * @return menu
	 */
	Permission convert(PermissionVO vo);

	/**
	 * convert
	 * @param menu
	 * @return menu vo
	 */
	PermissionVO convert(Permission entity);

	/**
	 * convert list
	 * @param menuList
	 * @return menu vo list
	 */
	List<PermissionVO> convertList(List<Permission> list);

}
