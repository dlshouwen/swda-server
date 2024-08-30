package com.dlshouwen.swda.bms.system.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * user mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * get user list
	 * @param params
	 * @return user list
	 */
	List<User> getUserList(Map<String, Object> params);

	/**
	 * get user by id
	 * @param userId
	 * @return user
	 */
	User getUserById(@Param("userId") Long userId);

	/**
	 * get role user list
	 * @param params
	 * @return user list
	 */
	List<User> getRoleUserList(Map<String, Object> params);

	/**
	 * get by user name
	 * @param username
	 * @return user
	 */
	default User getUserByUsername(String username) {
		return this.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
	}

	/**
	 * get by mobile
	 * @param mobile
	 * @return user
	 */
	default User getUserByMobile(String mobile) {
		return this.selectOne(Wrappers.<User>lambdaQuery().eq(User::getMobile, mobile));
	}

}