package com.htz.core.extra.grid.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htz.core.config.CONFIG;
import com.htz.core.extra.grid.dao.AdvanceQueryDao;
import com.htz.core.model.base.Handler;
import com.htz.core.model.base.SessionUser;
import com.htz.core.model.bms.AdvanceQuery;
import com.htz.core.model.bms.AdvanceQueryCondition;
import com.htz.core.model.bms.AdvanceQuerySort;
import com.htz.core.model.dict.OperationType;
import com.htz.core.utils.DataUtils;
import com.htz.core.utils.LogUtils;
import com.htz.core.utils.ObjectMapperUtils;
import com.htz.core.utils.ObjectUtils;

/**
 * 高级查询的处理类
 * @author liujingcheng@live.cn
 * @since hygea 6.0
 */
@Controller
@RequestMapping("/core/extra/grid/advance_query")
public class AdvanceQueryController {
	
	/** 功能根路径 */
	private String basePath = "core/extra/grid/";
	
	/** 数据操作对象 */
	private AdvanceQueryDao dao;
	
	/**
	 * 注入数据操作对象
	 * @param dao 数据操作对象
	 */
	@Resource(name="advanceQueryDao")
	public void setDao(AdvanceQueryDao dao){
		this.dao = dao;
	}
	
	/**
	 * 跳转到高级查询主页面
	 * @param id Grid编号
	 * @param model 反馈对象
	 * @return basePath + 'advanceQueryMain'
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String advanceQueryMain(@PathVariable String id, Model model) throws Exception {
		model.addAttribute("id", id);
		return basePath + "advanceQuery";
	}
	
	/**
	 * 获取方案数据
	 * @param request 请求对象
	 * @param functionCode 功能代码
	 * @param model 反馈对象
	 * @return 方案数据
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/tree", method=RequestMethod.POST)
	@ResponseBody
	public List<AdvanceQuery> getAdvanceQuery(HttpServletRequest request, String functionCode, Model model) throws Exception {
//		获取当前用户编号
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		if(sessionUser==null) {
			return null;
		}
		int userId = sessionUser.getUser_id();
//		获取高级查询是否过滤登录用户
		String advance_query_is_filter_user = DataUtils.getAttr(request.getServletContext(), "advance_query_is_filter_user");
//		查询所有的方案列表映射为String发送到前台
		List<AdvanceQuery> advanceQueryList = dao.getAdvanceQueryList(functionCode, userId, advance_query_is_filter_user);
//		跳转到对应页面
		return advanceQueryList;
	}
	
	/**
	 * 获取高级查询数据
	 * @param advanceQueryId 方案编号
	 * @return 高级查询数据
	 */
	@RequestMapping(value="/info/data", method=RequestMethod.POST)
	@ResponseBody
	public AdvanceQuery getAdvanceQueryConditionList(int advanceQueryId) throws Exception{
//		根据方案编号获取方案信息、高级查询条件列表数据、高级查询排序列表数据
		AdvanceQuery advanceQuery = dao.getAdvanceQuery(advanceQueryId);
		List<AdvanceQueryCondition> conditionList = dao.getAdvanceQueryConditionList(advanceQueryId);
		List<AdvanceQuerySort> sortList = dao.getAdvanceQuerySortList(advanceQueryId);
//		拼接数据并返回
		advanceQuery.setInfo("conditionList", conditionList);
		advanceQuery.setInfo("sortList", sortList);
		return advanceQuery;
	}
	
	/**
	 * 新增高级查询方案
	 * @param data 数据
	 * @param request 请求对象
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Handler addAdvanceQuery(@RequestBody Map<String, Object> data, HttpServletRequest request) throws Exception {
//		定义ajax响应对象
		Handler handler = new Handler();
//		获取查询方案、条件列表、排序列表
		AdvanceQuery advanceQuery = (AdvanceQuery)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQuery"), AdvanceQuery.class);
		@SuppressWarnings("unchecked")
		List<AdvanceQueryCondition> advanceQueryConditionList = (List<AdvanceQueryCondition>)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQueryConditionList"), new TypeReference<List<AdvanceQueryCondition>>(){});
		@SuppressWarnings("unchecked")
		List<AdvanceQuerySort> advanceQuerySortList = (List<AdvanceQuerySort>)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQuerySortList"), new TypeReference<List<AdvanceQuerySort>>(){});
//		检查查询方案错误
		if(!handler.validatorClass(advanceQuery)){
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQuery), handler.getMessage());
			return handler;
		}
//		检查条件错误
		for(AdvanceQueryCondition advanceQueryCondition : advanceQueryConditionList) {
			if(!handler.validatorClass(advanceQueryCondition)){
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQueryCondition), handler.getMessage());
				return handler;
			}
		}
//		检查排序错误
		for(AdvanceQuerySort advanceQuerySort : advanceQuerySortList) {
			if(!handler.validatorClass(advanceQuerySort)){
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQuerySort), handler.getMessage());
				return handler;
			}
		}
//		获取当前用户、当前时间
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		int userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		获取设置方案号、创建人、创建时间、编辑人、编辑时间
		advanceQuery.setCreator(userId);
		advanceQuery.setCreate_time(nowDate);
		advanceQuery.setEditor(userId);
		advanceQuery.setEdit_time(nowDate);
//		插入快速查询方案
		dao.insertAdvanceQuery(advanceQuery);
//		获取查询方案编号
		int advanceQueryId = dao.getLastInsertId("bms_advance_query");
//		定义排序码
		int conditionSort = 0;
//		遍历列表依次插入快速查询条件
		for(AdvanceQueryCondition advanceQueryCondition : advanceQueryConditionList){
//			获取设置查询编号、方案编号、排序号
			advanceQueryCondition.setAdvance_query_id(advanceQueryId);
			advanceQueryCondition.setSort(conditionSort);
//			插入快速查询数据
			dao.insertAdvanceQueryCondition(advanceQueryCondition);
//			排序自增
			conditionSort++;
		}
//		定义排序码
		int sortSort = 0;
//		遍历列表依次插入快速查询排序
		for(AdvanceQuerySort advanceQuerySort : advanceQuerySortList){
//			获取设置条件编号、方案编号、排序号
			advanceQuerySort.setAdvance_query_id(advanceQueryId);
			advanceQuerySort.setSort(sortSort);
//			插入快速查询数据
			dao.insertAdvanceQuerySort(advanceQuerySort);
//			排序自增
			sortSort++;
		}
//		设置成功信息
		handler.setSuccess();
		handler.setMessage("新增查询方案成功！");
		return handler;
	}
	
	/**
	 * 编辑高级查询方案
	 * @param data 数据
	 * @param request 请求对象
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Handler editAdvanceQuery(@RequestBody Map<String, Object> data, HttpServletRequest request) throws Exception {
//		定义ajax响应对象
		Handler handler = new Handler();
//		获取查询方案、条件列表、排序列表
		AdvanceQuery advanceQuery = (AdvanceQuery)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQuery"), AdvanceQuery.class);
		@SuppressWarnings("unchecked")
		List<AdvanceQueryCondition> advanceQueryConditionList = (List<AdvanceQueryCondition>)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQueryConditionList"), new TypeReference<List<AdvanceQueryCondition>>(){});
		@SuppressWarnings("unchecked")
		List<AdvanceQuerySort> advanceQuerySortList = (List<AdvanceQuerySort>)ObjectMapperUtils.getInstance().readValue((String)data.get("advanceQuerySortList"), new TypeReference<List<AdvanceQuerySort>>(){});
//		检查查询方案错误
		if(!handler.validatorClass(advanceQuery)){
//			记录操作日志
			LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQuery), handler.getMessage());
			return handler;
		}
//		检查条件错误
		for(AdvanceQueryCondition advanceQueryCondition : advanceQueryConditionList) {
			if(!handler.validatorClass(advanceQueryCondition)){
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQueryCondition), handler.getMessage());
				return handler;
			}
		}
//		检查排序错误
		for(AdvanceQuerySort advanceQuerySort : advanceQuerySortList) {
			if(!handler.validatorClass(advanceQuerySort)){
//				记录操作日志
				LogUtils.updateOperationLog(request, OperationType.INSERT, "新增查询方案（失败：数据验证错误）", ObjectUtils.toString(advanceQuerySort), handler.getMessage());
				return handler;
			}
		}
//		获取当前用户、当前时间
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
		int userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		获取设置方案号、创建人、创建时间、编辑人、编辑时间
		advanceQuery.setEditor(userId);
		advanceQuery.setEdit_time(nowDate);
//		编辑快速查询方案
		dao.updateAdvanceQuery(advanceQuery);
//		删除所有的快速查询条件记录
		dao.deleteAllAdvanceQueryCondition(advanceQuery.getAdvance_query_id());
//		删除所有的快速查询排序记录
		dao.deleteAllAdvanceQuerySort(advanceQuery.getAdvance_query_id());
//		定义排序码
		int conditionSort = 0;
//		遍历列表依次插入快速查询条件
		for(AdvanceQueryCondition advanceQueryCondition : advanceQueryConditionList){
//			获取设置查询编号、方案编号、排序号
			advanceQueryCondition.setAdvance_query_id(advanceQuery.getAdvance_query_id());
			advanceQueryCondition.setSort(conditionSort);
//			插入快速查询数据
			dao.insertAdvanceQueryCondition(advanceQueryCondition);
//			排序自增
			conditionSort++;
		}
//		定义排序码
		int sortSort = 0;
//		遍历列表依次插入快速查询排序
		for(AdvanceQuerySort advanceQuerySort : advanceQuerySortList){
//			获取设置条件编号、方案编号、排序号
			advanceQuerySort.setAdvance_query_id(advanceQuery.getAdvance_query_id());
			advanceQuerySort.setSort(sortSort);
//			插入快速查询数据
			dao.insertAdvanceQuerySort(advanceQuerySort);
//			排序自增
			sortSort++;
		}
//		设置成功信息
		handler.setSuccess();
		handler.setMessage("查询方案编辑成功！");
		return handler;
	}
	
	/**
	 * 删除高级查询方案
	 * @param advanceQueryId 方案编号
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Handler deleteAdvanceQuery(int advanceQueryId) throws Exception {
//		定义ajax响应对象
		Handler handler = new Handler();
//		删除快速查询方案
		dao.deleteAdvanceQuery(advanceQueryId);
//		删除所有的快速查询条件记录
		dao.deleteAllAdvanceQueryCondition(advanceQueryId);
//		删除所有的快速查询排序记录
		dao.deleteAllAdvanceQuerySort(advanceQueryId);
//		设置成功信息
		handler.setSuccess();
		handler.setMessage("查询方案删除成功！");
		return handler;
	}
	
	/**
	 * 复制高级查询方案
	 * @param request 请求对象
	 * @param advanceQueryId 方案编号
	 * @return ajax响应反馈
	 * @throws Exception 抛出全部异常
	 */
	@RequestMapping(value="/copy", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Handler copyAdvanceQuery(HttpServletRequest request, int advanceQueryId) throws Exception {
//		定义ajax响应对象
		Handler handler = new Handler();
//		获取登录用户
		SessionUser sessionUser = (SessionUser)request.getSession().getAttribute(CONFIG.SESSION_USER);
//		获取新的方案编号、创建人、创建时间
		int userId = sessionUser.getUser_id();
		Date nowDate = new Date();
//		复制快速查询方案
		dao.copyAdvanceQuery(advanceQueryId, userId, nowDate, userId, nowDate);
//		获取新查询方案编号
		int newAdvanceQueryId = dao.getLastInsertId("bms_advance_query");
//		根据方案编号获取高级查询条件列表数据
		List<AdvanceQueryCondition> conditionList = dao.getAdvanceQueryConditionList(advanceQueryId);
//		遍历查询条件列表数据
		for(AdvanceQueryCondition condition : conditionList){
//			获取查询编号、新的查询编号
			int conditionId = condition.getCondition_id();
//			复制快速查询条件数据
			dao.copyAdvanceQueryCondition(conditionId, newAdvanceQueryId);
		}
//		根据方案编号获取高级查询排序列表数据
		List<AdvanceQuerySort> sortList = dao.getAdvanceQuerySortList(advanceQueryId);
//		遍历查询排序列表数据
		for(AdvanceQuerySort sort : sortList){
//			获取查询编号、新的查询编号
			int sortId = sort.getSort_id();
//			复制快速查询排序数据
			dao.copyAdvanceQuerySort(sortId, newAdvanceQueryId);
		}
//		设置成功信息
		handler.setSuccess();
		handler.setMessage("复制查询方案成功！");
		return handler;
	}
	
}
