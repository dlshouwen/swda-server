package com.dlshouwen.swda.bms.permission.service;

import com.dlshouwen.swda.bms.permission.entity.UserSystem;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * user system service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IUserSystemService extends BaseService<UserSystem> {

	/**
	 * save or update
	 * @param userId
	 * @param systemIdList
	 */
	void saveOrUpdate(Long userId, List<Long> systemIdList);

	/**
	 * delete user system by system id list
	 * @param systemIdList
	 */
	void deleteUserSystemBySystemIdList(List<Long> systemIdList);

	/**
	 * delete user system by user id list
	 * @param userIdList
	 */
	void deleteUserSystemByUserIdList(List<Long> userIdList);

	/**
	 * get system id list
	 * @param userId
	 * @return system id list
	 */
	List<Long> getSystemIdList(Long userId);

}