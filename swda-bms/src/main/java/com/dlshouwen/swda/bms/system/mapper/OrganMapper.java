package com.dlshouwen.swda.bms.system.mapper;

import com.dlshouwen.swda.bms.system.entity.Organ;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * organ mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface OrganMapper extends BaseMapper<Organ> {

	/**
	 * get organ list
	 * @param params
	 * @return organ list
	 */
	List<Organ> getOrganList(Map<String, Object> params);

	/**
	 * get id and pid list
	 * @return organ list
	 */
	List<Organ> getIdAndPidList();

}