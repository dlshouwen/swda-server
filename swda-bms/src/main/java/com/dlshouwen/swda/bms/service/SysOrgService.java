package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.vo.OrganVO;

import java.util.List;

/**
 * organ service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

	/**
	 * get organ list
	 * @return organ vo list
	 */
	List<OrganVO> getList();

	/**
	 * save
	 * @param organVO
	 */
	void save(OrganVO vo);

	/**
	 * update
	 * @param organVO
	 */
	void update(OrganVO vo);

	/**
	 * delete
	 * @param id
	 */
	void delete(Long id);

	/**
	 * get sub organ id list
	 * @param id
	 * @return sub organ id list
	 */
	List<Long> getSubOrgIdList(Long id);

	/**
	 * get name list
	 * @param idList
	 * @return name list
	 */
	List<String> getNameList(List<Long> idList);

}