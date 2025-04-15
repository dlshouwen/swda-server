package com.dlshouwen.swda.bms.permission.mapper;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.permission.entity.RoleMenu;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * role menu mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * get menu id list
	 * @param roleId
	 * @return menu id list
	 */
	List<Long> getMenuIdList(@Param("roleId") Long roleId);

}
