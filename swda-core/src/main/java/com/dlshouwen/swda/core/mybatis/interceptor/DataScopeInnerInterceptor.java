package com.dlshouwen.swda.core.mybatis.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * data scope inner interceptor
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class DataScopeInnerInterceptor implements InnerInterceptor {

	/**
	 * before query
	 * @param executor
	 * @param ms
	 * @param parameter
	 * @param rowBounds
	 * @param resultHandler
	 * @param boundSql
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) {
//		get scope
		DataScope scope = getDataScope(parameter);
//		if null then return
		if (scope == null || StrUtil.isBlank(scope.getSqlFilter())) {
			return;
		}
//		get select sql
		String buildSql = getSelect(boundSql.getSql(), scope);
//		mapping bound sql
		PluginUtils.mpBoundSql(boundSql).sql(buildSql);
	}

	/**
	 * get data scope
	 * @param parameter
	 * @return
	 */
	private DataScope getDataScope(Object parameter) {
//		if null then return
		if (parameter == null) {
			return null;
		}
//		is parameter map
		if (parameter instanceof Map<?, ?> parameterMap) {
//			for each parameter map
			for (Map.Entry<?, ?> entry : parameterMap.entrySet()) {
//				has value then return
				if (entry.getValue() != null && entry.getValue() instanceof DataScope) {
					return (DataScope) entry.getValue();
				}
			}
		} else if (parameter instanceof DataScope) {
//			get value
			return (DataScope) parameter;
		}

		return null;
	}

	/**
	 * get select
	 * @param buildSql
	 * @param scope
	 * @return
	 */
	private String getSelect(String buildSql, DataScope scope) {
		try {
//			parse select sql
			Select select = (Select) CCJSqlParserUtil.parse(buildSql);
//			get select body
			PlainSelect plainSelect = (PlainSelect) select.getPlainSelect();
//			get where
			Expression expression = plainSelect.getWhere();
//			if null
			if (expression == null) {
//				set new condition
				plainSelect.setWhere(new StringValue(scope.getSqlFilter()));
			} else {
//				add and condition
				AndExpression andExpression = new AndExpression(expression, new StringValue(scope.getSqlFilter()));
				plainSelect.setWhere(andExpression);
			}
//			return select sql
			return select.toString();
		} catch (JSQLParserException e) {
			return buildSql;
		}
	}

}