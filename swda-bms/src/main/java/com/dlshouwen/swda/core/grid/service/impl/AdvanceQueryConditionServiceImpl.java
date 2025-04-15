package com.dlshouwen.swda.bms.core.grid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.core.grid.convert.AdvanceQueryConditionConvert;
import com.dlshouwen.swda.bms.core.grid.entity.AdvanceQueryCondition;
import com.dlshouwen.swda.bms.core.grid.mapper.AdvanceQueryConditionMapper;
import com.dlshouwen.swda.bms.core.grid.service.IAdvanceQueryConditionService;
import com.dlshouwen.swda.bms.core.grid.vo.AdvanceQueryConditionVO;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;

/**
 * advance query condition service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class AdvanceQueryConditionServiceImpl extends BaseServiceImpl<AdvanceQueryConditionMapper, AdvanceQueryCondition> implements IAdvanceQueryConditionService {

	/**
	 * get advance query condition list
	 * @param advanceQueryId
	 * @return advance query condition list
	 */
	@Override
	public List<AdvanceQueryConditionVO> getAdvanceQueryConditionList(Long advanceQueryId) {
//		get wrapper: search by advance query id, sort by sort asc
		LambdaQueryWrapper<AdvanceQueryCondition> wrapper = Wrappers.<AdvanceQueryCondition>lambdaQuery();
		wrapper.eq(AdvanceQueryCondition::getAdvanceQueryId, advanceQueryId);
		wrapper.orderByAsc(AdvanceQueryCondition::getSort);
//		get advance query condition list
		List<AdvanceQueryCondition> conditionList = this.list(wrapper);
//		conert to vo list for return
		return AdvanceQueryConditionConvert.INSTANCE.convert2VOList(conditionList);
	}

	/**
	 * add advance query condition list
	 * @param conditionVOList
	 */
	@Override
	public void addAdvanceQueryConditionList(List<AdvanceQueryConditionVO> conditionVOList) {
//		convert vo list to entity
		List<AdvanceQueryCondition> conditionList = AdvanceQueryConditionConvert.INSTANCE.convertList(conditionVOList);
//		add advance query condition list
		this.saveBatch(conditionList);
	}

	/**
	 * delete advance query condition list
	 * @param advanceQueryId
	 */
	@Override
	public void deleteAdvanceQueryConditionList(Long advanceQueryId) {
//		delete advance query condition list
		this.remove(Wrappers.<AdvanceQueryCondition>lambdaQuery().eq(AdvanceQueryCondition::getAdvanceQueryId, advanceQueryId));
	}

}
