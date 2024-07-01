package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.vo.SysDictVO;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * dict mapper
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysDictDataDao extends BaseMapper<Dict> {

	/**
	 * get list for sql
	 * @param sql
	 * @return dict list
	 */
	@Select("${sql}")
	List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);

}
