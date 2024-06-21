package com.dlshouwen.swda.core.interceptor;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.entity.base.Data;
import com.dlshouwen.swda.core.dict.CallResult;
import com.dlshouwen.swda.core.dict.CallType;
import com.dlshouwen.swda.core.dict.OperationType;
import com.dlshouwen.swda.core.entity.log.DataLog;
import com.dlshouwen.swda.core.utils.ExceptionUtils;
import com.dlshouwen.swda.core.utils.HttpContextUtils;
import com.dlshouwen.swda.core.utils.IpUtils;
import com.dlshouwen.swda.core.utils.JsonUtils;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class MyBatisLogInterceptor implements Interceptor {

	/** is write log */
	private boolean isWriteLog = false;
	
	/** data log */
	private DataLog dataLog = new DataLog();

	/** redis cache */
	private final RedisCache redisCache;

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
		if (!dataLog.getCallSource().endsWith("DataConfig") && !dataLog.getCallSource().endsWith("DataLogBatchQueue") && !dataLog.getCallSource().endsWith("OperationLogBatchQueue")) {
			isWriteLog = true;
		} else {
			isWriteLog = false;
		}
//		is write log
		if (isWriteLog) {
//			set call type, call result, start time, execute type
			dataLog.setCallType(CallType.MYBATIS);
			dataLog.setCallResult(CallResult.SUCCESS);
			dataLog.setStartTime(LocalDateTime.now());
			dataLog.setExecuteType(invocation.getMethod().getName());
//			get reqeust
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//			set ip
			dataLog.setIp(IpUtils.getIp(request));
//			set user id, user name, organ id, organ name, tanent id
			UserDetail user = SecurityUser.getUser();
			if(user!=null) {
				dataLog.setUserId(user.getUserId());
				dataLog.setUserName(user.getUsername());
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
//			set operation type
			dataLog.setOperationType(OperationType.UNKNOWN);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT)
				dataLog.setOperationType(OperationType.SELECT);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT)
				dataLog.setOperationType(OperationType.INSERT);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE)
				dataLog.setOperationType(OperationType.UPDATE);
			if (mappedStatement.getSqlCommandType() == SqlCommandType.DELETE)
				dataLog.setOperationType(OperationType.DELETE);
//			get bound sql
			BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
//			get paramter to json
			String params = JsonUtils.toJsonString(boundSql.getParameterMappings());
//			get return sql
			String returnSQL = getSql(configuration, boundSql, mappedStatement.getId(), 0);
//			set operation sql, params
			dataLog.setOperationSql(returnSQL);
			dataLog.setParams(params);
		}
//		defined result
		Object result = null;
		try {
//			proceed
			result = invocation.proceed();
//			is write log
			if (isWriteLog) {
//				set execute result, result type
				dataLog.setExecuteResult(JsonUtils.toJsonString(result));
				dataLog.setResultType(result.getClass().getName());
//				if only store error
				String data_log_only_store_error = MapUtil.getStr(Data.attr, "data_log_only_store_error");
				if ("1".equals(data_log_only_store_error)) {
					isWriteLog = false;
				}
//				filter store type
				String data_log_store_type = MapUtil.getStr(Data.attr, "data_log_store_type");
				if ("2".equals(data_log_store_type) && OperationType.SELECT == dataLog.getOperationType()) {
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