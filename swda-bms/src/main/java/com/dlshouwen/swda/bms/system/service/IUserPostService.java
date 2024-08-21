package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.bms.system.entity.UserPost;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * user post service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IUserPostService extends BaseService<UserPost> {

	/**
	 * save or update
	 * @param userId
	 * @param postIdList
	 */
	void saveOrUpdate(Long userId, List<Long> postIdList);

	/**
	 * delete user post by post id list
	 * @param postIdList
	 */
	void deleteUserPostByPostIdList(List<Long> postIdList);

	/**
	 * delete user post by user id list
	 * @param userIdList
	 */
	void deleteUserPostByUserIdList(List<Long> userIdList);

	/**
	 * get post id list
	 * @param userId
	 * @return post id list
	 */
	List<Long> getPostIdList(Long userId);

}