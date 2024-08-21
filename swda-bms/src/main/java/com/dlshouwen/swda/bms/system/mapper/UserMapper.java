package com.dlshouwen.swda.bms.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * user mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
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
		return this.selectOne(new QueryWrapper<User>().eq("username", username));
	}

	/**
	 * get by mobile
	 * @param mobile
	 * @return user
	 */
	default User getUserByMobile(String mobile) {
		return this.selectOne(new QueryWrapper<User>().eq("mobile", mobile));
	}

}