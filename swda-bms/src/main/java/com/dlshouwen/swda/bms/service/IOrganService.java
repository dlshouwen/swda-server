package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Organ;
import com.dlshouwen.swda.bms.vo.OrganVO;

import java.util.List;

/**
 * organ service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IOrganService extends BaseService<Organ> {

	/**
	 * get organ list
	 * @return organ vo list
	 */
	List<OrganVO> getOrganList();
	
	/**
	 * get organ data
	 * @param organId
	 * @return organ vo
	 */
	OrganVO getOrganData(Long organId);

	/**
	 * add organ
	 * @param organVO
	 */
	void addOrgan(OrganVO vo);

	/**
	 * update organ
	 * @param organVO
	 */
	void updateOrgan(OrganVO vo);

	/**
	 * delete organ
	 * @param organId
	 */
	void deleteOrgan(Long id);

	/**
	 * get sub organ id list
	 * @param organId
	 * @return sub organ id list
	 */
	List<Long> getSubOrganIdList(Long organId);

	/**
	 * get organ name list
	 * @param organIdList
	 * @return organ name list
	 */
	List<String> getOrganNameList(List<Long> organIdList);

}