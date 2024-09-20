package com.dlshouwen.swda.bms.core.grid.service;

import java.util.List;

import com.dlshouwen.swda.bms.core.grid.entity.AdvanceQuery;
import com.dlshouwen.swda.bms.core.grid.vo.AdvanceQueryVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import jakarta.validation.Valid;

/**
 * advance query service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IAdvanceQueryService extends BaseService<AdvanceQuery> {

	/**
	 * get advance query list
	 * @param functionCode
	 * @return advance query list
	 */
	List<AdvanceQueryVO> getAdvanceQueryList(String functionCode);

	/**
	 * get advance query data
	 * @param advanceQueryId
	 * @return advance query data
	 */
	AdvanceQueryVO getAdvanceQueryData(Long advanceQueryId);

	/**
	 * add advance query
	 * @param advanceQueryVO
	 * @return advance query id
	 */
	Long addAdvanceQuery(@Valid AdvanceQueryVO advanceQueryVO);

	/**
	 * update advance query
	 * @param advanceQueryVO
	 */
	void updateAdvanceQuery(@Valid AdvanceQueryVO advanceQueryVO);

	/**
	 * delete advance query
	 * @param advanceQueryId
	 */
	void deleteAdvanceQuery(Long advanceQueryId);

}
