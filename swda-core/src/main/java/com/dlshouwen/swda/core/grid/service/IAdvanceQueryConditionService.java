package com.dlshouwen.swda.core.grid.service;

import java.util.List;

import com.dlshouwen.swda.core.grid.entity.AdvanceQueryCondition;
import com.dlshouwen.swda.core.grid.vo.AdvanceQueryConditionVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * advance query condition service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IAdvanceQueryConditionService extends BaseService<AdvanceQueryCondition> {

	/**
	 * get advance query condition list
	 * @param advanceQueryId
	 * @return advance query condition list
	 */
	List<AdvanceQueryConditionVO> getAdvanceQueryConditionList(Long advanceQueryId);

	/**
	 * add advance query condition list
	 * @param conditionVOList
	 */
	void addAdvanceQueryConditionList(List<AdvanceQueryConditionVO> conditionVOList);

	/**
	 * delete advance query condition list
	 * @param advanceQueryId
	 */
	void deleteAdvanceQueryConditionList(Long advanceQueryId);

}
