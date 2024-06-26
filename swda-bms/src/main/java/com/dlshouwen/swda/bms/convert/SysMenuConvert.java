package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysMenuEntity;
import com.dlshouwen.swda.bms.vo.SysMenuVO;
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
	SysMenuEntity convert(SysMenuVO vo);

	/**
	 * convert
	 * @param menu
	 * @return menu vo
	 */
	SysMenuVO convert(SysMenuEntity entity);

	/**
	 * convert list
	 * @param menuList
	 * @return menu vo list
	 */
	List<SysMenuVO> convertList(List<SysMenuEntity> list);

}
