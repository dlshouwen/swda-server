package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.vo.SysDictVO;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * 字典数据
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysDictDataDao extends BaseMapper<SysDictDataEntity> {

    @Select("${sql}")
    List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);
}