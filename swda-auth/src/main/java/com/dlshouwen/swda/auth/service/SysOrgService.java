package com.dlshouwen.swda.auth.service;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysOrgEntity;
import com.dlshouwen.swda.auth.vo.SysOrgVO;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * organ service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

	/**
	 * get list
	 * @return organ vo list
	 */
	List<SysOrgVO> getList();

	/**
	 * save
	 * @param organVO
	 */
	void save(SysOrgVO vo);

	/**
	 * update
	 * @param organVO
	 */
	void update(SysOrgVO vo);

	/**
	 * delete
	 * @param organId
	 */
	void delete(Long id);

	/**
	 * get sub organ id list
	 * @param organId
	 * @return sub organ id list
	 */
	List<Long> getSubOrgIdList(Long id);

	/**
	 * get organ name list
	 * @param organIdList
	 * @return organ name list
	 */
	List<String> getNameList(List<Long> idList);

}