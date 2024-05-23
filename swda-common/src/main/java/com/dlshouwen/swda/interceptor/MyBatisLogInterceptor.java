package com.dlshouwen.swda.interceptor;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.base.LoginUser;
import com.dlshouwen.swda.entity.dict.CallResult;
import com.dlshouwen.swda.entity.dict.CallType;
import com.dlshouwen.swda.entity.dict.OperationType;
import com.dlshouwen.swda.entity.log.DataLog;
import com.dlshouwen.swda.queue.DataLogBatchQueue;
import com.dlshouwen.swda.utils.ExceptionUtils;
import com.dlshouwen.swda.utils.IpUtils;
import com.dlshouwen.swda.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;
import java.util.regex.Matcher;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * mybatis log interceptor
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update",args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
public class MyBatisLogInterceptor implements Interceptor {

	/** data log batch queue */
	@Autowired
	DataLogBatchQueue<DataLog> dataLogBatchQueue;

	/**
	 * intercept
	 * @param invocation
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
//		defined data log
		DataLog dataLog = new DataLog();
//		defined is write log
		boolean isWriteLog = false;
//		get call source / line no
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for(int i=1; i<stack.length; i++){
				String callSource = stack[i].getClassName();
				int lineNo = stack[i].getLineNumber();
				if (callSource.startsWith("com.dlshouwen")) {
					dataLog.setCallSource(callSource);
					dataLog.setLineNo(lineNo);
					break;
				}
			}
		}
//		some source not write log
		if(!dataLog.getCallSource().endsWith("DataConfig") && !dataLog.getCallSource().endsWith("DataLogBatchQueue") && !dataLog.getCallSource().endsWith("OperationLogBatchQueue")){
			isWriteLog = true;
		}else{
			isWriteLog = false;
		}
//		if write log
		if(isWriteLog){
//			get request
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//			set call type / call result / start time / execute type
			dataLog.setCallType(CallType.MYBATIS);
			dataLog.setCallResult(CallResult.SUCCESS);
			dataLog.setStartTime(new Date());
			dataLog.setExecuteType(invocation.getMethod().getName());
//			set ip
			dataLog.setIp(IpUtils.getIpAddr(request));
//			set user / organ
			String token = request.getHeader("token");
			LoginUser loginUser = null;
			if(!StringUtils.isEmpty(token)){
				loginUser = TokenUtils.getLoginUser(token);
			}
			dataLog.setUserId(loginUser==null?null:loginUser.getUserId());
			dataLog.setUserName(loginUser==null?null:loginUser.getUserName());
			dataLog.setOrganId(loginUser==null?null:loginUser.getOrganId());
			dataLog.setOrganName(loginUser==null?null:loginUser.getOrganName());
//			get args
			Object[] args = invocation.getArgs();
//			get mapped statement
			MappedStatement mappedStatement = (MappedStatement) args[0];
			mappedStatement.getStatementType();
//			get user mapper class
			Configuration configuration = mappedStatement.getConfiguration();
//			set call source: [ type.method ]
			dataLog.setCallSource(mappedStatement.getId());
//			get parameter
			Object parameterObject = args[1];
//			set operation type
			dataLog.setOperationType(OperationType.UNKNOWN);
			if(mappedStatement.getSqlCommandType()== SqlCommandType.SELECT) dataLog.setOperationType(OperationType.SELECT);
			if(mappedStatement.getSqlCommandType()== SqlCommandType.INSERT) dataLog.setOperationType(OperationType.INSERT);
			if(mappedStatement.getSqlCommandType()== SqlCommandType.UPDATE) dataLog.setOperationType(OperationType.UPDATE);
			if(mappedStatement.getSqlCommandType()== SqlCommandType.DELETE) dataLog.setOperationType(OperationType.DELETE);
//			get bound sql
			BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
//			convert patameter to json
			String params = new ObjectMapper().writeValueAsString(boundSql.getParameterMappings());
//			get operation sql
			String operationSQL = getSql(configuration, boundSql, mappedStatement.getId(), 0);
//			set operation sql / params
			dataLog.setOperationSql(operationSQL);
			dataLog.setParams(params);
		}
//		defined result
		Object result = null;
		try{
//			proceed
			result = invocation.proceed();
//			if write log
			if(isWriteLog){
//				set execute result / result type
				dataLog.setExecuteResult(new ObjectMapper().writeValueAsString(result));
				dataLog.setResultType(result.getClass().getName());
//				if only store error then not write
				if("1".equals(Data.attr.get("data_log_only_store_error"))) {
					isWriteLog = false;
				}
//				if only store dml
				if("2".equals(Data.attr.get("data_log_store_type")) && OperationType.SELECT.equals(dataLog.getOperationType())){
					isWriteLog = false;
				}
			}
		}catch(Throwable e){
//			if write log
			if(isWriteLog) {
//				set call result / error reason
				dataLog.setCallResult(CallResult.FAILURE);
				dataLog.setErrorReason(ExceptionUtils.toString(e));
			}
//			throw
			throw e;
		}finally {
//			if write log
			if(isWriteLog){
//				set end time / cost / log id
				dataLog.setEndTime(new Date());
				dataLog.setCost((int) (dataLog.getEndTime().getTime()-dataLog.getStartTime().getTime()));
				dataLog.setLogId(IdWorker.getId());
//				add to queue
				dataLogBatchQueue.add(dataLog);
			}
		}
		return result;
	}

	/**
	 * <p>封装目标对象</p>
	 * plugin方法是拦截器用于封装目标对象的，通过该方法我们可以返回目标对象本身，也可以返回一个它的代理。
	 * 当返回的是代理的时候我们可以对其中的方法进行拦截来调用intercept方法，当然也可以调用其他方法
	 * 对于plugin方法而言，其实Mybatis已经为我们提供了一个实现。Mybatis中有一个叫做Plugin的类，
	 * 里面有一个静态方法wrap(Object target,Interceptor interceptor)，通过该方法可以决定要返回的对象是目标对象还是对应的代理。
	 * @param o 对象
	 * @return 封装后对象
	 */
	@Override
	public Object plugin(Object o) {
		if (o instanceof Executor) {
			return Plugin.wrap(o, this);
		}
		return o;
	}

	/**
	 * <p>设置属性</p>
	 * setProperties方法是用于在Mybatis配置文件中指定一些属性的
	 * 这个方法在Configuration初始化当前的Interceptor时就会执行
	 * @param properties 属性
	 */
	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * <p>获取SQL</p>
	 * @param configuration 配置项
	 * @param boundSql 原生SQL
	 * @param sqlId SQL编号
	 * @param time 时间
	 * @return 解码后SQL
	 */
	private static String getSql(Configuration configuration, BoundSql boundSql,
								 String sqlId, long time) {
		String sql = getSql(configuration, boundSql);
		StringBuilder str = new StringBuilder(100);
		str.append(sqlId);
		str.append(":");
		str.append(sql);
		return str.toString();
	}

	/**
	 * <p>获取参数值</p>
	 * @param obj 对象
	 * @return 参数值
	 */
	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(
					DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
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
	 * 显示SQL
	 * @param configuration 配置
	 * @param boundSql 原始SQL
	 * @return 解码后SQL
	 */
	private static String getSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (!parameterMappings.isEmpty() && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?",
						Matcher.quoteReplacement(getParameterValue(parameterObject)));

			} else {
				MetaObject metaObject = configuration
						.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql
								.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
					} else {
						sql = sql.replaceFirst("\\?", "缺失");
					}
				}
			}
		}
		return sql;
	}

}