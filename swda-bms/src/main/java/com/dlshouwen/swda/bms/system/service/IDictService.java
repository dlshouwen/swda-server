package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import java.util.List;

/**
 * dict data service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IDictService extends BaseService<Dict> {

	/**
	 * get dict list
	 * @param dictType
	 * @param query
	 * @return dict list
	 */
	PageResult<DictVO> getDictList(String dictType, Query<Dict> query);
	
	/**
	 * get dict data
	 * @param dictId
	 * @return dict
	 */
	DictVO getDictData(Long dictId);

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
	 * @param dictIdList
	 */
	void deleteDict(List<Long> dictIdList);

}