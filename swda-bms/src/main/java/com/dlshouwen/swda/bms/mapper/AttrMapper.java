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
public interface AttrMapper extends BaseMapper<Attr> {

	/**
	 * is exist
	 * @param attrId
	 * @return is exist
	 */
	default boolean isExist(String attrId) {
		return this.exists(new QueryWrapper<Attr>().eq("attr_id", attrId));
	}

	/**
	 * get
	 * @param attrId
	 * @return params
	 */
	default Attr get(String attrId) {
		return this.selectOne(new QueryWrapper<Attr>().eq("attrId", attrId));
	}

}