package com.dlshouwen.swda.core.grid.service;

import java.util.List;

import com.dlshouwen.swda.core.grid.entity.AdvanceQuerySort;
import com.dlshouwen.swda.core.grid.vo.AdvanceQuerySortVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * advance query sort service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IAdvanceQuerySortService extends BaseService<AdvanceQuerySort> {

	/**
	 * get advance query sort list
	 * @param advanceQueryId
	 * @return advance query sort list
	 */
	List<AdvanceQuerySortVO> getAdvanceQuerySortList(Long advanceQueryId);

	/**
	 * add advance query sort list
	 * @param sortVOList
	 */
	void addAdvanceQuerySortList(List<AdvanceQuerySortVO> sortVOList);

	/**
	 * delete advance query sort list
	 * @param advanceQueryId
	 */
	void deleteAdvanceQuerySortList(Long advanceQueryId);

}
