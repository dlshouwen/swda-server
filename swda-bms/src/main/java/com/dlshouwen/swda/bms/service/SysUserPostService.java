package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysUserPostEntity;

import java.util.List;

/**
 * user post service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysUserPostService extends BaseService<SysUserPostEntity> {

	/**
	 * save or update
	 * @param userId
	 * @param postIdList
	 */
	void saveOrUpdate(Long userId, List<Long> postIdList);

	/**
	 * delete by post id list
	 * @param postIdList
	 */
	void deleteByPostIdList(List<Long> postIdList);

	/**
	 * delete by user id list
	 * @param userIdList
	 */
	void deleteByUserIdList(List<Long> userIdList);

	/**
	 * get post id list
	 * @param userId
	 * @return post id list
	 */
	List<Long> getPostIdList(Long userId);

}