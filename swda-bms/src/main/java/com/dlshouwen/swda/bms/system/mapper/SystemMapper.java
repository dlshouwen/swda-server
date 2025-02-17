package com.dlshouwen.swda.bms.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.system.entity.System;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

/**
 * system mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface SystemMapper extends BaseMapper<System> {

	/**
	 * get login user system list
	 * @param userId
	 * @return login user system list
	 */
	List<System> getLoginUserSystemList(@Param("userId") Long userId);

}