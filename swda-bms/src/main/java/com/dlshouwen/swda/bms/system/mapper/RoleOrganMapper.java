package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.RoleOrgan;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * role data scope mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface RoleOrganMapper extends BaseMapper<RoleOrgan> {

	/**
	 * get organ id list
	 * @param roleId
	 * @return organ id list
	 */
	List<Long> getOrganIdList(@Param("roleId") Long roleId);

	/**
	 * get data scope list
	 * @param userId
	 * @return data scope list
	 */
	List<Long> getDataScopeList(@Param("userId") Long userId);

}