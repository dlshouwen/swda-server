package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.DictType;
import com.dlshouwen.swda.bms.system.vo.DictTypeVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import java.util.List;

/**
 * dict type
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IDictTypeService extends BaseService<DictType> {

	/**
	 * get dict type page result
	 * @param query
	 * @return dict type page result
	 */
	PageResult<DictTypeVO> getDictTypePageResult(Query<DictType> query);
	
	/**
	 * get dict type data
	 * @param dictTypeId
	 * @return dict type
	 */
	DictTypeVO getDictTypeData(Long dictTypeId);

	/**
	 * add dict type
	 * @param dictTypeVO
	 */
	void addDictType(DictTypeVO dictTypeVO);

	/**
	 * update dict type
	 * @param dictTypeVO
	 */
	void updateDictType(DictTypeVO dictTypeVO);

	/**
	 * delete dict type
	 * @param idList
	 */
	void deleteDictType(List<Long> dictTypeIdList);

	/**
	 * get sql dict list
	 * @param dictTypeId
	 * @return dict list
	 */
	List<DictVO> getSqlDictList(Long dictTypeId);

	/**
	 * refresh dict trans cache
	 */
	void refreshDictTransCache();

}