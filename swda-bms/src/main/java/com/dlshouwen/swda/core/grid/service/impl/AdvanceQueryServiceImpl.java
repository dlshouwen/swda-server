package com.dlshouwen.swda.core.grid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.core.base.dict.ZeroOne;
import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.grid.convert.AdvanceQueryConvert;
import com.dlshouwen.swda.core.grid.entity.AdvanceQuery;
import com.dlshouwen.swda.core.grid.mapper.AdvanceQueryMapper;
import com.dlshouwen.swda.core.grid.service.IAdvanceQueryService;
import com.dlshouwen.swda.core.grid.vo.AdvanceQueryVO;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * advance query service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class AdvanceQueryServiceImpl extends BaseServiceImpl<AdvanceQueryMapper, AdvanceQuery> implements IAdvanceQueryService {

	/**
	 * get advance query list
	 * @param functionCode
	 * @return advance query list
	 */
	@Override
	public List<AdvanceQueryVO> getAdvanceQueryList(String functionCode) {
//		get user detail
		UserDetail user = SecurityUser.getUser();
//		if no user
		if(user==null) {
//			return null
			return null;
		}
//		get attr: advance_query_is_filter_user
		String advance_query_is_filter_user = Data.attr.get("advance_query_is_filter_user");
//		get query wrapper
		LambdaQueryWrapper<AdvanceQuery> wrapper = Wrappers.lambdaQuery();
//		filter function code
		wrapper.eq(AdvanceQuery::getFunctionCode, functionCode);
//		filter user
		if(String.valueOf(ZeroOne.YES).equals(advance_query_is_filter_user)) {
			wrapper.eq(AdvanceQuery::getCreator, user.getUserId());
		}
//		sort by edit time desc
		wrapper.orderByDesc(AdvanceQuery::getUpdateTime);
//		get advance query list
		List<AdvanceQuery> advanceQueryList = this.list(wrapper);
//		convert to vo list for return
		return AdvanceQueryConvert.INSTANCE.convert2VOList(advanceQueryList);
	}

	/**
	 * get advance query data
	 * @param advanceQueryId
	 * @return advance query data
	 */
	@Override
	public AdvanceQueryVO getAdvanceQueryData(Long advanceQueryId) {
//		get advance query data
		AdvanceQuery advanceQuery = this.getById(advanceQueryId);
//		convert to vo for return
		return AdvanceQueryConvert.INSTANCE.convert2VO(advanceQuery);
	}

	/**
	 * add advance query
	 * @param advanceQueryVO
	 * @return advance query id
	 */
	@Override
	public Long addAdvanceQuery(@Valid AdvanceQueryVO advanceQueryVO) {
//		convert to advance query
		AdvanceQuery advanceQuery = AdvanceQueryConvert.INSTANCE.convert(advanceQueryVO);
//		add advance query
		this.save(advanceQuery);
//		return advance query id
		return advanceQuery.getAdvanceQueryId();
	}

	/**
	 * update advance query
	 * @param advanceQueryVO
	 */
	@Override
	public void updateAdvanceQuery(@Valid AdvanceQueryVO advanceQueryVO) {
//		convert to advance query
		AdvanceQuery advanceQuery = AdvanceQueryConvert.INSTANCE.convert(advanceQueryVO);
//		update advance query
		this.updateById(advanceQuery);
	}

	/**
	 * delete advance query
	 * @param advanceQueryId
	 */
	@Override
	public void deleteAdvanceQuery(Long advanceQueryId) {
//		delete advance query
		this.removeById(advanceQueryId);
	}

}
