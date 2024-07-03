package com.dlshouwen.swda.core.utils;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlshouwen.swda.core.dict.ConditionLogic;
import com.dlshouwen.swda.core.dict.ConditionType;
import com.dlshouwen.swda.core.dict.SortLogic;
import com.dlshouwen.swda.core.entity.grid.Condition;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.entity.grid.Sort;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 表格工具类
 * </p>
 * 
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
public class GridUtils {
	
	/**
	 * query
	 * @param baseMapper
	 * @param pager
	 * @param <T>
	 */
	public static <T> IPage<T> query(BaseMapper<T> baseMapper, Query<T> query) {
//		construct wrapper
		QueryWrapper<T> wrapper = GridUtils.constructQueryWrapper(query);
//		select page
		IPage<T> page = baseMapper.selectPage(query.getPage(), wrapper);
//		return page
		return page;
	}
	
	/**
	 * construct query wrapper
	 */
	public static <T> QueryWrapper<T> constructQueryWrapper(Query<T> query) {
//		defined query wrapper
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
//		put parameter
		Map<String, Object> params = new HashMap<>();
		if (query.getManualQueryParameters() != null)
			params.putAll(query.getManualQueryParameters());
		if (query.getFastQueryParameters() != null)
			params.putAll(query.getFastQueryParameters());
//		if has params
		if (params != null) {
//			for each params
			for (String key : params.keySet()) {
//				get value
				String value = MapUtils.getString(params, key).trim();
//				value empty -> continue
				if (StringUtils.isEmpty(value)) {
					continue;
				}
//				has _
				if (key.indexOf("_") != -1) {
//					ge key
					String field = key.substring(key.indexOf("_") + 1, key.length());
//					equals
					if (key.startsWith("eq_")) {
						queryWrapper.eq(field, value);
						continue;
					}
//					not equals
					if (key.startsWith("ne_")) {
						queryWrapper.ne(field, value);
						continue;
					}
//					like
					if (key.startsWith("lk_")) {
						queryWrapper.like(field, value);
						continue;
					}
//					right like
					if (key.startsWith("rl_")) {
						queryWrapper.likeRight(field, value);
						continue;
					}
//					left like
					if (key.startsWith("ll_")) {
						queryWrapper.likeLeft(field, value);
						continue;
					}
//					is null
					if (key.startsWith("in_")) {
						queryWrapper.isNull(field);
						continue;
					}
//					is not null
					if (key.startsWith("inn_")) {
						queryWrapper.isNotNull(field);
						continue;
					}
//					greater than
					if (key.startsWith("gt_")) {
						queryWrapper.gt(field, value);
						continue;
					}
//					greater than equals
					if (key.startsWith("ge_")) {
						queryWrapper.ge(field, value);
						continue;
					}
//					less than
					if (key.startsWith("lt_")) {
						queryWrapper.lt(field, value);
						continue;
					}
//					less than equals
					if (key.startsWith("le_")) {
						queryWrapper.le(field, value);
						continue;
					}
				}
			}
		}
//		if has conditions
		if (query.getAdvanceQueryConditions() != null && query.getAdvanceQueryConditions().size() > 0) {
//			builde sql
			StringBuilder sql = new StringBuilder(" and ");
//			defined args
			List<Object> args = new ArrayList<>();
//			for each condition
			for (Condition condition : query.getAdvanceQueryConditions()) {
//				append left parentheses
				sql.append(" ").append(condition.getLeftParentheses()).append(" ");
//				append condition
				if (condition.getConditionType()==ConditionType.EQUALS) {
//					equals
					sql.append(" ").append(condition.getConditionField()).append(" = ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.NOT_EQUALS) {
//					not equals
					sql.append(" ").append(condition.getConditionField()).append(" != ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.LIKE) {
//					like
					sql.append(" ").append(condition.getConditionField()).append(" like ? escape '\' ");
					args.add("%" + SqlUtils.escape(condition.getConditionValue()) + "%");
				} else if (condition.getConditionType()==ConditionType.RIGHT_LIKE) {
//					right like
					sql.append(" ").append(condition.getConditionField()).append(" like ? escape '\' ");
					args.add(SqlUtils.escape(condition.getConditionValue()) + "%");
				} else if (condition.getConditionType()==ConditionType.LEFT_LIKE) {
//					left like
					sql.append(" ").append(condition.getConditionField()).append(" like ? escape '\' ");
					args.add("%" + SqlUtils.escape(condition.getConditionValue()));
				} else if (condition.getConditionType()==ConditionType.GREATER_THAN) {
//					greater than
					sql.append(" ").append(condition.getConditionField()).append(" > ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.GREATER_THAN_EQUALS) {
//					greater than equals
					sql.append(" ").append(condition.getConditionField()).append(" >= ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.LESS_THAN) {
//					less than
					sql.append(" ").append(condition.getConditionField()).append(" < ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.LESS_THAN_EQUALS) {
//					less than equals
					sql.append(" ").append(condition.getConditionField()).append(" <= ? ");
					args.add(condition.getConditionValue());
				} else if (condition.getConditionType()==ConditionType.NULL) {
//					null
					sql.append(" ").append(condition.getConditionField()).append(" is null ");
				} else if (condition.getConditionType()==ConditionType.NOT_NULL) {
//					not null
					sql.append(" ").append(condition.getConditionField()).append(" is not null ");
				}
//				append right parentheses
				sql.append(" ").append(condition.getRightParentheses()).append(" ");
//				append condition logic
				if(condition.getConditionLogic()==ConditionLogic.AND) {
					sql.append(" and ");
				}
				if(condition.getConditionLogic()==ConditionLogic.OR) {
					sql.append(" or ");
				}
			}
//			apply sql
			queryWrapper.apply(sql.toString(), args.toArray());
		}
//		if has sorts
		if (query.getAdvanceQuerySorts() != null && query.getAdvanceQuerySorts().size() > 0) {
//			for each sort
			for (Sort sort : query.getAdvanceQuerySorts()) {
//				asc
				if (sort.getSortLogic()==SortLogic.ASC) {
					queryWrapper.orderByAsc(sort.getSortField());
				}
//				desc
				if (sort.getSortLogic()==SortLogic.DESC) {
					queryWrapper.orderByDesc(sort.getSortField());
				}
			}
		}
//		return query wrapper
		return queryWrapper;
	}

	/**
	 * <p>
	 * 查询
	 * </p>
	 * 
	 * @param _class   类对象
	 * @param pager    分页对象
	 * @param template 数据源
	 * @param sql      查询SQL
	 * @param args     其他参数
	 * @param <T>      泛型对象
	 */
	public static <T> void query(Class _class, Pager<T> pager, JdbcTemplate template, String sql, Object... args) {
		// 如果查询器未创建则创建新的查询器
		if (pager.getQueryWrapper() == null)
			pager.setQueryWrapper(new QueryWrapper<>());
		// 构建查询器
		pager.constructQueryWrapper();
		// 封装执行SQL
		String executeSQL = "select * from (" + sql + ") _swda_query_table_alisa_ where "
				+ pager.getQueryWrapper().getTargetSql();
		// 处理执行参数
		List<Object> executeArgs = new ArrayList<>();
		for (Object arg : args) {
			executeArgs.add(arg);
		}
		for (String key : pager.getQueryWrapper().getParamNameValuePairs().keySet()) {
			executeArgs.add(pager.getQueryWrapper().getParamNameValuePairs().get(key));
		}
		// 统计数据并设置到对象中
		String countSQL = "select count(*) from (" + executeSQL + ") _swda_query_count_table_alisa_ ";
		int total = template.queryForObject(countSQL, Integer.class, executeArgs.toArray());
		pager.getPage().setTotal(total);
		// 处理分页及参数
		executeSQL += " limit ?, ?";
		executeArgs.add(pager.getPage().getCurrent());
		executeArgs.add(pager.getPage().getSize());
		// 设置页码参数
		pager.getPage().setPages((total + pager.getPage().getSize() - 1) / pager.getPage().getSize());
		// 查询数据并写入到分页对象中
		if (_class == Map.class) {
			List<Map<String, Object>> records = template.queryForList(executeSQL, executeArgs.toArray());
			pager.getPage().setRecords((List<T>) records);
		} else {
			List<T> records = template.query(executeSQL, new ClassRowMapper<T>(_class), executeArgs.toArray());
			pager.getPage().setRecords(records);
		}
		// 设置上一页下一页
		pager.setHasPrevious(pager.getPage().hasPrevious());
		pager.setHasNext(pager.getPage().hasPrevious());
	}

}