package com.dlshouwen.swda.bms.permission.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dlshouwen.swda.bms.permission.entity.UserSystem;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * user system
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface UserSystemMapper extends BaseMapper<UserSystem> {

	/**
	 * get system id list
	 * @param systemId
	 * @return system id list
	 */
	@Select("select system_id from bms_user_system where user_id=#{userId} and deleted=0")
	List<Long> getSystemIdList(@Param("userId") Long userId);

}