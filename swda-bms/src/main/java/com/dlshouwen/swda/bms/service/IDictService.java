package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.vo.DictVO;

import java.util.List;

/**
 * dict data service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IDictService extends BaseService<Dict> {

	/**
	 * get dict list
	 * @param query
	 * @return dict list
	 */
	PageResult<DictVO> getDictList(Query<Dict> query);
	
	/**
	 * get dict data
	 * @param dictCategoryId
	 * @param dictId
	 * @return dict
	 */
	DictVO getDictData(String dictCategoryId, String dictId);

	/**
	 * add dict
	 * @param dictVO
	 */
	void addDict(DictVO dictVO);

	/**
	 * update dict
	 * @param dictVO
	 */
	void updateDict(DictVO dictVO);

	/**
	 * delete Dict
	 * @param dictCategoryId
	 * @param dictIdList
	 */
	void deleteDict(String dictCategoryId, List<String> dictIdList);

}