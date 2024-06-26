package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysDictTypeEntity;
import com.dlshouwen.swda.bms.query.SysDictTypeQuery;
import com.dlshouwen.swda.bms.vo.SysDictTypeVO;
import com.dlshouwen.swda.bms.vo.SysDictVO;

import java.util.List;

/**
 * 数据字典
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

	PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

	void save(SysDictTypeVO vo);

	void update(SysDictTypeVO vo);

	void delete(List<Long> idList);

	/**
	 * 获取动态SQL数据
	 */
	List<SysDictVO.DictData> getDictSql(Long id);

	/**
	 * 获取全部字典列表
	 */
	List<SysDictVO> getDictList();

	/**
	 * 刷新字典缓存
	 */
	void refreshTransCache();

}