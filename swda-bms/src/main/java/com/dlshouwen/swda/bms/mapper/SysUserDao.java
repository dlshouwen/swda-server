package com.dlshouwen.swda.bms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

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
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	/**
	 * get user list
	 * @param params
	 * @return user list
	 */
	List<SysUserEntity> getList(Map<String, Object> params);

	/**
	 * get user by id
	 * @param id
	 * @return user
	 */
	SysUserEntity getById(@Param("id") Long id);

	/**
	 * get role user list
	 * @param params
	 * @return user
	 */
	List<SysUserEntity> getRoleUserList(Map<String, Object> params);

	/**
	 * get by user name
	 * @param username
	 * @return user
	 */
	default SysUserEntity getByUsername(String username) {
		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("username", username));
	}

	/**
	 * get by mobile
	 * @param mobile
	 * @return user
	 */
	default SysUserEntity getByMobile(String mobile) {
		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("mobile", mobile));
	}

}