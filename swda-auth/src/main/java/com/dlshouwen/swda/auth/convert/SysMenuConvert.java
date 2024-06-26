package com.dlshouwen.swda.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.auth.entity.SysMenuEntity;
import com.dlshouwen.swda.auth.vo.SysMenuVO;

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
	 * @param menduVO
	 * @return menu
	 */
	SysMenuEntity convert(SysMenuVO vo);

	/**
	 * convert
	 * @param menu
	 * @return menuVO
	 */
	SysMenuVO convert(SysMenuEntity entity);

	/**
	 * convert list
	 * @param menuList
	 * @return menuVOList
	 */
	List<SysMenuVO> convertList(List<SysMenuEntity> list);

}
