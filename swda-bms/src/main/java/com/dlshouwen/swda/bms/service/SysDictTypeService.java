package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.DictCategory;
import com.dlshouwen.swda.bms.query.SysDictTypeQuery;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.vo.SysDictVO;

import java.util.List;

/**
 * dict type
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysDictTypeService extends BaseService<DictCategory> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<DictCategoryVO> page(SysDictTypeQuery query);

	/**
	 * save
	 * @param dictTypeVO
	 */
	void save(DictCategoryVO vo);

	/**
	 * update
	 * @param dictTypeVO
	 */
	void update(DictCategoryVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

	/**
	 * get dict sql
	 * @param id
	 * @return dict list
	 */
	List<SysDictVO.DictData> getDictSql(Long id);

	/**
	 * get dict list
	 * @return dict vo list
	 */
	List<SysDictVO> getDictList();

	/**
	 * refresh trans cache
	 */
	void refreshTransCache();

}