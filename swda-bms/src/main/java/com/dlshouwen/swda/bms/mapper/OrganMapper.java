package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dlshouwen.swda.bms.entity.Organ;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * organ mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
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