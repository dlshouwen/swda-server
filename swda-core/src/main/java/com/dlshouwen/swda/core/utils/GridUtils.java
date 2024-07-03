package com.dlshouwen.swda.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlshouwen.swda.core.dict.ConditionLogic;
import com.dlshouwen.swda.core.dict.ConditionType;
import com.dlshouwen.swda.core.dict.SortLogic;
import com.dlshouwen.swda.core.entity.grid.Condition;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.entity.grid.Sort;
import com.dlshouwen.swda.core.handler.ClassRowMapper;
import com.dlshouwen.swda.core.mapper.BaseMapper;

/**
 * grid utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
	 * query
	 * @param _class
	 * @param pager
	 * @param template
	 * @param sql
	 * @param args
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public static <T> IPage<T> query(Class<T> _class, Query<T> query, JdbcTemplate template, String sql, Object... args) {
//		construct query wrapper
		QueryWrapper<T> wrapper = constructQueryWrapper(query);
//		set execute sql
		String executeSQL = "select * from (" + sql + ") _swda_query_table_alisa_ where "+wrapper.getTargetSql();
//		defined execute args
		List<Object> executeArgs = new ArrayList<>();
//		add args
		for (Object arg : args) {
			executeArgs.add(arg);
		}
//		add wrapper args
		for (String key : wrapper.getParamNameValuePairs().keySet()) {
			executeArgs.add(wrapper.getParamNameValuePairs().get(key));
		}
//		count sql
		String countSQL = "select count(*) from (" + executeSQL + ") _swda_query_count_table_alisa_ ";
//		get total
		Long total = template.queryForObject(countSQL, Long.class, executeArgs.toArray());
//		set total
		query.getPage().setTotal(total);
//		set limit
		executeSQL += " limit ?, ?";
		executeArgs.add(query.getPage().getCurrent());
		executeArgs.add(query.getPage().getSize());
//		set pages
		query.getPage().setPages((total + query.getPage().getSize() - 1) / query.getPage().getSize());
//		get record
		if (_class == Map.class) {
			List<Map<String, Object>> records = template.queryForList(executeSQL, executeArgs.toArray());
			query.getPage().setRecords((List<T>) records);
		} else {
			List<T> records = template.query(executeSQL, new ClassRowMapper<T>(_class), executeArgs.toArray());
			query.getPage().setRecords(records);
		}
//		return page
		return query.getPage();
	}

}