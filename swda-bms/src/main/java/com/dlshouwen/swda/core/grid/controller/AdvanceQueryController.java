package com.dlshouwen.swda.core.grid.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.service.IAdvanceQueryConditionService;
import com.dlshouwen.swda.core.grid.service.IAdvanceQueryService;
import com.dlshouwen.swda.core.grid.service.IAdvanceQuerySortService;
import com.dlshouwen.swda.core.grid.vo.AdvanceQueryConditionVO;
import com.dlshouwen.swda.core.grid.vo.AdvanceQuerySortVO;
import com.dlshouwen.swda.core.grid.vo.AdvanceQueryVO;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * advance query
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/grid/advance_query")
@AllArgsConstructor
@Tag(name = "advance query")
public class AdvanceQueryController {
	
	/** advance query service */
	private IAdvanceQueryService advanceQueryService;
	
	/** advance query condition service */
	private IAdvanceQueryConditionService advanceQueryConditionService;
	
	/** advance query sort service */
	private IAdvanceQuerySortService advanceQuerySortService;
	
	/**
	 * get advance query list
	 * @param functionCode
	 * @return advance query list
	 */
	@PostMapping(value="/list")
	@Operation(name = "get advance query list", type = OperateType.SEARCH)
	public R<List<AdvanceQueryVO>> getAdvanceQuery(@RequestBody Map<String, String> params) {
//		get function fode
		String functionCode = MapUtil.getStr(params, "functionCode");
//		get user detail
		UserDetail user = SecurityUser.getUser();
//		if no user
		if(user==null) {
//			return
			return R.error("用户未授权");
		}
//		get advance query list
		List<AdvanceQueryVO> advanceQueryList = advanceQueryService.getAdvanceQueryList(functionCode);
//		return
		return R.ok(advanceQueryList);
	}
	
	/**
	 * get advance query data
	 * @param advanceQueryId
	 * @return advance query
	 */
	@GetMapping(value="/{advanceQueryId}/data")
	@Operation(name = "get advance query data", type = OperateType.SEARCH)
	public R<AdvanceQueryVO> getAdvanceQueryData(@PathVariable("advanceQueryId") Long advanceQueryId) throws Exception{
//		get advance query
		AdvanceQueryVO advanceQuery = advanceQueryService.getAdvanceQueryData(advanceQueryId);
//		get advance query condition list
		List<AdvanceQueryConditionVO> conditionList = advanceQueryConditionService.getAdvanceQueryConditionList(advanceQueryId);
//		get advance query sort list
		List<AdvanceQuerySortVO> sortList = advanceQuerySortService.getAdvanceQuerySortList(advanceQueryId);
//		set data to advance query
		advanceQuery.setConditionList(conditionList);
		advanceQuery.setSortList(sortList);
//		return advance query
		return R.ok(advanceQuery);
	}
	
	/**
	 * add advance query
	 * @param advance query
	 * @return result
	 */
	@PostMapping(value="/add")
	@Operation(name = "add advance query", type = OperateType.INSERT)
	public R<String> addAdvanceQuery(@RequestBody @Valid AdvanceQueryVO advanceQuery) {
//		set null
		advanceQuery.setAdvanceQueryId(null);
//		add advance query
		Long advanceQueryId = advanceQueryService.addAdvanceQuery(advanceQuery);
//		get advance query condition list
		List<AdvanceQueryConditionVO> conditionList = advanceQuery.getConditionList();
//		condition list is not empty
		if(conditionList != null && conditionList.size()>0) {
//			set advance query id to condition list
			conditionList.forEach(condition -> condition.setAdvanceQueryId(advanceQueryId));
//			defined sequence
			int sequence = 0;
//			for each condition
			for(AdvanceQueryConditionVO condition : conditionList) {
//				set null
				condition.setConditionId(null);
//				set sort
				condition.setSort(sequence);
//				sequence ++
				sequence ++;
			}
//			add advance query condition list
			advanceQueryConditionService.addAdvanceQueryConditionList(conditionList);
		}
//		get advance query sort list
		List<AdvanceQuerySortVO> sortList = advanceQuery.getSortList();
//		sort list is not empty
		if(sortList != null && sortList.size() > 0) {
//			set advance query id to sort list
			sortList.forEach(sort -> sort.setAdvanceQueryId(advanceQueryId));
//			defined sequence
			int sequence = 0;
//			for each condition
			for(AdvanceQuerySortVO sort : sortList) {
//				set null
				sort.setSortId(null);
//				set sort
				sort.setSort(sequence);
//				sequence ++
				sequence ++;
			}
//			add advance query sort list
			advanceQuerySortService.addAdvanceQuerySortList(sortList);
		}
//		return
		return R.ok();
	}
	
	/**
	 * update advance query
	 * @param advance query
	 * @return result
	 */
	@PostMapping(value="/update")
	@Operation(name = "update advance query", type = OperateType.UPDATE)
	public R<String> updateAdvanceQuery(@RequestBody @Valid AdvanceQueryVO advanceQuery) {
//		update advance query
		advanceQueryService.updateAdvanceQuery(advanceQuery);
//		get advance query id
		Long advanceQueryId = advanceQuery.getAdvanceQueryId();
//		delete advance query condition list
		advanceQueryConditionService.deleteAdvanceQueryConditionList(advanceQueryId);
//		get advance query condition list
		List<AdvanceQueryConditionVO> conditionList = advanceQuery.getConditionList();
//		condition list is not empty
		if(conditionList != null && conditionList.size()>0) {
//			set advance query id to condition list
			conditionList.forEach(condition -> condition.setAdvanceQueryId(advanceQueryId));
//			defined sequence
			int sequence = 0;
//			for each condition
			for(AdvanceQueryConditionVO condition : conditionList) {
//				set null
				condition.setConditionId(null);
//				set sort
				condition.setSort(sequence);
//				sequence ++
				sequence ++;
			}
//			add advance query condition list
			advanceQueryConditionService.addAdvanceQueryConditionList(conditionList);
		}
//		delete advance query sort list
		advanceQuerySortService.deleteAdvanceQuerySortList(advanceQueryId);
//		get advance query sort list
		List<AdvanceQuerySortVO> sortList = advanceQuery.getSortList();
//		sort list is not empty
		if(sortList != null && sortList.size() > 0) {
//			set advance query id to sort list
			sortList.forEach(sort -> sort.setAdvanceQueryId(advanceQueryId));
//			defined sequence
			int sequence = 0;
//			for each condition
			for(AdvanceQuerySortVO sort : sortList) {
//				set null
				sort.setSortId(null);
//				set sort
				sort.setSort(sequence);
//				sequence ++
				sequence ++;
			}
//			add advance query sort list
			advanceQuerySortService.addAdvanceQuerySortList(sortList);
		}
//		return
		return R.ok();
	}
	
	/**
	 * delete advance query
	 * @param advanceQueryId
	 * @return result
	 */
	@PostMapping(value="/delete")
	@Operation(name = "delete advance query", type = OperateType.DELETE)
	public R<String> deleteAdvanceQuery(@RequestBody Long advanceQueryId) {
//		delete advance query
		advanceQueryService.deleteAdvanceQuery(advanceQueryId);
//		delete advance query condition list
		advanceQueryConditionService.deleteAdvanceQueryConditionList(advanceQueryId);
//		delete advance query sort list
		advanceQuerySortService.deleteAdvanceQuerySortList(advanceQueryId);
//		return
		return R.ok();
	}
	
	/**
	 * copy advance query
	 * @param advanceQueryId
	 * @return result
	 */
	@PostMapping(value="/copy")
	@Operation(name = "copy advance query", type = OperateType.INSERT)
	public R<String> copyAdvanceQuery(@RequestBody Long advanceQueryId) {
//		get advance query
		AdvanceQueryVO advanceQuery = advanceQueryService.getAdvanceQueryData(advanceQueryId);
//		get advance query condition list
		List<AdvanceQueryConditionVO> conditionList = advanceQueryConditionService.getAdvanceQueryConditionList(advanceQueryId);
//		get advance query sort list
		List<AdvanceQuerySortVO> sortList = advanceQuerySortService.getAdvanceQuerySortList(advanceQueryId);
//		add advance query
		Long newAdvanceQueryId = advanceQueryService.addAdvanceQuery(advanceQuery);
//		condition list is not empty
		if(conditionList != null && conditionList.size()>0) {
//			set advance query id to condition list
			conditionList.forEach(condition -> { condition.setConditionId(null);condition.setAdvanceQueryId(newAdvanceQueryId); });
//			add advance query condition list
			advanceQueryConditionService.addAdvanceQueryConditionList(conditionList);
		}
//		sort list is not empty
		if(sortList != null && sortList.size() > 0) {
//			set advance query id to sort list
			sortList.forEach(sort -> { sort.setSortId(null);sort.setAdvanceQueryId(newAdvanceQueryId); });
//			add advance query sort list
			advanceQuerySortService.addAdvanceQuerySortList(sortList);
		}
//		return
		return R.ok();
	}
	
}
