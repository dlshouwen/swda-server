package com.dlshouwen.swda.bms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	List<SysUserEntity> getList(Map<String, Object> params);

	SysUserEntity getById(@Param("id") Long id);

	List<SysUserEntity> getRoleUserList(Map<String, Object> params);

	default SysUserEntity getByUsername(String username) {
		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("username", username));
	}

	default SysUserEntity getByMobile(String mobile) {
		return this.selectOne(new QueryWrapper<SysUserEntity>().eq("mobile", mobile));
	}
}