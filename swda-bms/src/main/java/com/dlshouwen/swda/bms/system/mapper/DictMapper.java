package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * dict mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * get list for sql
	 * @param sql
	 * @return dict list
	 */
	@Select("${sql}")
	List<Dict> getListForSql(@Param("sql") String sql);

}
