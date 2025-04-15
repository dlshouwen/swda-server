package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Menu;
import com.dlshouwen.swda.bms.system.vo.MenuVO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * menu convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuConvert {
	
	/** instance */
	MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

	/**
	 * convert
	 * @param menuVO
	 * @return menu
	 */
	Menu convert(MenuVO vo);

	/**
	 * convert to vo
	 * @param menu
	 * @return menu vo
	 */
	@Mapping(source = "menuId", target = "id")
	@Mapping(source = "preMenuId", target = "pid")
	@Mapping(source = "menuName", target = "name")
	MenuVO convert2VO(Menu entity);

	/**
	 * convert to vo list
	 * @param menuList
	 * @return menu vo list
	 */
	List<MenuVO> convert2VOList(List<Menu> list);

}
