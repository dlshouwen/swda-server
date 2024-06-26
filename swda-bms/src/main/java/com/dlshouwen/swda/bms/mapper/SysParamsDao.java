package com.dlshouwen.swda.bms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.bms.entity.SysParamsEntity;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 参数管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysParamsDao extends BaseMapper<SysParamsEntity> {

	default boolean isExist(String paramKey) {
		return this.exists(new QueryWrapper<SysParamsEntity>().eq("param_key", paramKey));
	}

	default SysParamsEntity get(String paramKey) {
		return this.selectOne(new QueryWrapper<SysParamsEntity>().eq("param_key", paramKey));
	}
}