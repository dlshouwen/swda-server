package com.dlshouwen.swda.core.log.intercepter;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.core.common.cache.RedisCache;
import com.dlshouwen.swda.core.common.constant.Constant;
import com.dlshouwen.swda.core.common.entity.Data;
import com.dlshouwen.swda.core.common.utils.ExceptionUtils;
import com.dlshouwen.swda.core.common.utils.HttpContextUtils;
import com.dlshouwen.swda.core.common.utils.IpUtils;
import com.dlshouwen.swda.core.common.utils.JsonUtils;
import com.dlshouwen.swda.core.log.dict.CallResult;
import com.dlshouwen.swda.core.log.dict.CallType;
import com.dlshouwen.swda.core.log.dict.ExecuteType;
import com.dlshouwen.swda.core.log.dict.OperationType;
import com.dlshouwen.swda.core.log.dto.DataLogDTO;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mybatis log interceptor
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Intercepts({
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) 
})
@Component
public class MyBatisLogInterceptor implements Interceptor {

	/** is write log */
	private boolean isWriteLog = false;
	
	/** data log */
	private DataLogDTO dataLog = new DataLogDTO();

	/** redis cache */
	@Autowired
	private RedisCache redisCache;

	/**
	 * intercept
	 * @param invocation
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
//		set call source, line no
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for (int i = 1; i < stack.length; i++) {
				String callSource = stack[i].getClassName();
				int lineNo = stack[i].getLineNumber();
				if (callSource.startsWith("com.dlshouwen")) {
					dataLog.setCallSource(callSource);
					dataLog.setLineNo(lineNo);
					break;
				}
			}
		}
//		set ignore
		if (!dataLog.getCallSource().endsWith("DataConfig") && !dataLog.getCallSource().endsWith("DataLogServiceImpl")) {
			isWriteLog = true;
		} else {
			isWriteLog = false;
		}
//		is write log
		if (isWriteLog) {
//			set call type, call result, start time, execute method
			dataLog.setCallType(CallType.MYBATIS);
			dataLog.setCallResult(CallResult.SUCCESS);
			dataLog.setStartTime(LocalDateTime.now());
			dataLog.setExecuteMethod(invocation.getMethod().getName());
//			get reqeust
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//			set ip
			dataLog.setIp(IpUtils.getIp(request));
//			set user id, user name, organ id, organ name, tanent id
			UserDetail user = SecurityUser.getUser();
			if(user!=null) {
				dataLog.setUserId(user.getUserId());
				dataLog.setRealName(user.getRealName());
				dataLog.setOrganId(user.getOrganId());
				dataLog.setOrganName(user.getOrganName());
				dataLog.setTenantId(user.getTenantId());
			}
//			get args
			Object[] args = invocation.getArgs();
			MappedStatement mappedStatement = (MappedStatement) args[0];
			mappedStatement.getStatementType();
//			get mapper config
			Configuration configuration = mappedStatement.getConfiguration();
//			set call source
			dataLog.setCallSource(mappedStatement.getId());
//			get parameter
			Object parameterObject = args[1];
//			set execute type
			dataLog.setExecuteType(ExecuteType.UNKNOWN);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT)
				dataLog.setExecuteType(ExecuteType.SELECT);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT)
				dataLog.setExecuteType(ExecuteType.INSERT);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE)
				dataLog.setExecuteType(ExecuteType.UPDATE);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.DELETE)
				dataLog.setExecuteType(ExecuteType.DELETE);
//			get bound sql
			BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
//			get paramter to json
			String params = JsonUtils.toJsonString(boundSql.getParameterMappings());
//			get return sql
			String returnSQL = getSql(configuration, boundSql, mappedStatement.getId(), 0);
//			set execute sql, execute params
			dataLog.setExecuteSql(returnSQL);
			dataLog.setExecuteParams(params);
		}
//		defined result
		Object result = null;
		try {
//			proceed
			result = invocation.proceed();
//			is write log
			if (isWriteLog) {
//				set execute result, execute result type
				dataLog.setExecuteResult(JsonUtils.toJsonString(result));
				dataLog.setExecuteResultClass(result.getClass().getName());
//				if only store error
				String data_log_only_store_error = MapUtil.getStr(Data.attr, "data_log_only_store_error");
				if ("1".equals(data_log_only_store_error)) {
					isWriteLog = false;
				}
//				filter store type
				String data_log_store_type = MapUtil.getStr(Data.attr, "data_log_store_type");
				if ("2".equals(data_log_store_type) && OperationType.SELECT == dataLog.getExecuteType()) {
					isWriteLog = false;
				}
			}
		} catch (Throwable e) {
//			is write log
			if (isWriteLog) {
//				set call result, error reason
				dataLog.setCallResult(CallResult.FAILURE);
				dataLog.setErrorReason(ExceptionUtils.toString(e));
			}
//			throw e
			throw e;
		} finally {
//			is write log
			if (isWriteLog) {
//				set end time, cost, log id
				dataLog.setEndTime(LocalDateTime.now());
				dataLog.setCost((int) Duration.between(dataLog.getStartTime(), dataLog.getEndTime()).toMillis());
				dataLog.setLogId(IdWorker.getId());
//				save log
				redisCache.leftPush(Constant.DATA_LOG_KEY, dataLog, RedisCache.NOT_EXPIRE);
			}
		}
//		return result
		return result;
	}

	/**
	 * plugin
	 * @param o
	 * @return
	 */
	@Override
	public Object plugin(Object o) {
		if (o instanceof Executor) {
			return Plugin.wrap(o, this);
		}
		return o;
	}

	/**
	 * set properties
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * get sql
	 * @param configuration
	 * @param boundSql
	 * @param sqlId
	 * @param time
	 * @return
	 */
	private static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
		String sql = getSql(configuration, boundSql);
		StringBuilder str = new StringBuilder(100);
		str.append(sqlId);
		str.append(":");
		str.append(sql);
		return str.toString();
	}

	/**
	 * get paramteter value
	 * @param obj
	 * @return params
	 */
	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}

	/**
	 * get sql
	 * @param configuration
	 * @param boundSql
	 * @return
	 */
	private static String getSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (!parameterMappings.isEmpty() && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					} else {
						sql = sql.replaceFirst("\\?", "[Lose!]");
					}
				}
			}
		}
		return sql;
	}

}