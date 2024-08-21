package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.entity.PageResult;
import com.dlshouwen.swda.core.grid.entity.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.vo.DictVO;

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