package com.dlshouwen.swda.bms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.bms.entity.Attr;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * params mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysParamsDao extends BaseMapper<Attr> {

	/**
	 * is exist
	 * @param paramKey
	 * @return is exist
	 */
	default boolean isExist(String paramKey) {
		return this.exists(new QueryWrapper<Attr>().eq("param_key", paramKey));
	}

	/**
	 * get
	 * @param paramKey
	 * @return params
	 */
	default Attr get(String paramKey) {
		return this.selectOne(new QueryWrapper<Attr>().eq("param_key", paramKey));
	}

}