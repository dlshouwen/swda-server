package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.SysRoleMenuEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * role menu mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

	/**
	 * get menu id list
	 * @param roleId
	 * @return menu id list
	 */
	List<Long> getMenuIdList(@Param("roleId") Long roleId);

}
