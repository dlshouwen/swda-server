package com.dlshouwen.swda.core.grid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dlshouwen.swda.core.grid.convert.AdvanceQuerySortConvert;
import com.dlshouwen.swda.core.grid.entity.AdvanceQuerySort;
import com.dlshouwen.swda.core.grid.mapper.AdvanceQuerySortMapper;
import com.dlshouwen.swda.core.grid.service.IAdvanceQuerySortService;
import com.dlshouwen.swda.core.grid.vo.AdvanceQuerySortVO;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;

/**
 * advance query sort service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AdvanceQuerySortServiceImpl extends BaseServiceImpl<AdvanceQuerySortMapper, AdvanceQuerySort> implements IAdvanceQuerySortService {

	/**
	 * get advance query sort list
	 * @param advanceQueryId
	 * @return advance query sort list
	 */
	@Override
	public List<AdvanceQuerySortVO> getAdvanceQuerySortList(Long advanceQueryId) {
//		get wrapper: search by advance query id, order by sort asc
		QueryWrapper<AdvanceQuerySort> wrapper = new QueryWrapper<AdvanceQuerySort>();
		wrapper.eq("advanceQueryId", advanceQueryId);
		wrapper.orderByAsc("sort");
//		get advance query sort list
		List<AdvanceQuerySort> sortList = this.list(wrapper);
//		convert to vo list for return
		return AdvanceQuerySortConvert.INSTANCE.convert2VOList(sortList);
	}

	/**
	 * add advance query sort list
	 * @param sortVOList
	 */
	@Override
	public void addAdvanceQuerySortList(List<AdvanceQuerySortVO> sortVOList) {
//		convert to entity list
		List<AdvanceQuerySort> sortList = AdvanceQuerySortConvert.INSTANCE.convertList(sortVOList);
//		add advance query sort list
		this.saveBatch(sortList);
	}

	/**
	 * delete advance query sort list
	 * @param advanceQueryId
	 */
	@Override
	public void deleteAdvanceQuerySortList(Long advanceQueryId) {
//		get wrapper: search by advance query id
		QueryWrapper<AdvanceQuerySort> wrapper = new QueryWrapper<AdvanceQuerySort>();
		wrapper.eq("advanceQueryId", advanceQueryId);
//		delete advance query sort list
		this.remove(wrapper);
	}
	

}
