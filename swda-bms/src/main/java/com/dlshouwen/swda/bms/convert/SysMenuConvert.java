package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysMenuEntity;
import com.dlshouwen.swda.bms.vo.PremissionVO;
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
	SysMenuEntity convert(PremissionVO vo);

	/**
	 * convert
	 * @param menu
	 * @return menu vo
	 */
	PremissionVO convert(SysMenuEntity entity);

	/**
	 * convert list
	 * @param menuList
	 * @return menu vo list
	 */
	List<PremissionVO> convertList(List<SysMenuEntity> list);

}
