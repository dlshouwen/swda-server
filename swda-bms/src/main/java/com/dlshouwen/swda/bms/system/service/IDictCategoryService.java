package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.entity.PageResult;
import com.dlshouwen.swda.core.grid.entity.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.DictCategory;
import com.dlshouwen.swda.bms.system.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import java.util.List;

/**
 * dict category
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IDictCategoryService extends BaseService<DictCategory> {

	/**
	 * get dict category list
	 * @param query
	 * @return dict category list
	 */
	PageResult<DictCategoryVO> getDictCategoryList(Query<DictCategory> query);
	
	/**
	 * get dict category data
	 * @param dictCategoryId
	 * @return dict category
	 */
	DictCategoryVO getDictCategoryData(String dictCategoryId);

	/**
	 * add dict category
	 * @param dictCategoryVO
	 */
	void addDictCategory(DictCategoryVO dictCategoryVO);

	/**
	 * update dict category
	 * @param dictCategoryVO
	 */
	void updateDictCategory(DictCategoryVO dictCategoryVO);

	/**
	 * delete dict category
	 * @param idList
	 */
	void deleteDictCategory(List<Long> dictCategoryIdList);

	/**
	 * get sql dict list
	 * @param dictCategoryId
	 * @return dict list
	 */
	List<DictVO> getSqlDictList(String dictCategoryId);

	/**
	 * refresh dict trans cache
	 */
	void refreshDictTransCache();

}