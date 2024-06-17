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
 * <p>MyBatis数据日志拦截器</p>
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update",args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
@Component
@AllArgsConstructor
public class MyBatisLogInterceptor implements Interceptor {

    /** 是否需要写入数据日志 */
    private boolean isWriteLog = false;
    /** 数据日志对象 */
    private DataLog dataLog = new DataLog();
    
	private final RedisCache redisCache;

    /**
     * <p>拦截方法</p>
     * @param invocation 调用对象
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 通过线程获取调用类及行号并设置到数据日志中
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
        // 如果是数据配置或日志写入本身调用则不记录日志
        if(!dataLog.getCallSource().endsWith("DataConfig") &&
                !dataLog.getCallSource().endsWith("DataLogBatchQueue") &&
                !dataLog.getCallSource().endsWith("OperationLogBatchQueue")){
            isWriteLog = true;
        }else{
            isWriteLog = false;
        }
        if(isWriteLog){
            // 写入执行类型、执行结果、开始时间、执行类别
            dataLog.setCallType(CallType.MYBATIS);
            dataLog.setCallResult(CallResult.SUCCESS);
            dataLog.setStartTime(LocalDateTime.now());
            dataLog.setExecuteType(invocation.getMethod().getName());
            // 获取request
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            // 设置IP
            dataLog.setIp(IpUtils.getIpAddr(request));
            // 设置用户编号、用户名称、机构编号、机构名称
    		UserDetail user = SecurityUser.getUser();
            dataLog.setUserId(user==null?null:user.getUserId());
            dataLog.setUserName(user==null?null:user.getUsername());
            dataLog.setOrganId(user==null?null:user.getOrganId());
            dataLog.setOrganName(user==null?null:user.getOrganName());
            // 获取参数
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            mappedStatement.getStatementType();
            // 当前SQL使用的是哪个Mapper,即哪个Mapper类
            Configuration configuration = mappedStatement.getConfiguration();
            // 执行当前SQL的Mapper id,其组成 [ 类型.方法 ]
            dataLog.setCallSource(mappedStatement.getId());
            // 存放的是SQL的参数[它是一个实例对象]
            Object parameterObject = args[1];
            // 设置操作类型
            dataLog.setOperationType(OperationType.UNKNOWN);
            if(mappedStatement.getSqlCommandType()== SqlCommandType.SELECT) dataLog.setOperationType(OperationType.SELECT);
            if(mappedStatement.getSqlCommandType()== SqlCommandType.INSERT) dataLog.setOperationType(OperationType.INSERT);
            if(mappedStatement.getSqlCommandType()== SqlCommandType.UPDATE) dataLog.setOperationType(OperationType.UPDATE);
            if(mappedStatement.getSqlCommandType()== SqlCommandType.DELETE) dataLog.setOperationType(OperationType.DELETE);
            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
            // 将参数值转成json字符串
            String params = JsonUtils.toJsonString(boundSql.getParameterMappings());
            // 返回拼装好参数的SQL
            String returnSQL = getSql(configuration, boundSql, mappedStatement.getId(), 0);
            // 设置操作SQL及参数
            dataLog.setOperationSql(returnSQL);
            dataLog.setParams(params);
        }
        Object result = null;
        try{
            // 执行程序
            result = invocation.proceed();
            if(isWriteLog){
                // 设置执行结果、结果类型
                dataLog.setExecuteResult(JsonUtils.toJsonString(result));
                dataLog.setResultType(result.getClass().getName());
                // 如果仅存储错误，则不进行存储
                String data_log_only_store_error = MapUtil.getStr(Data.attr, "data_log_only_store_error");
                if("1".equals(data_log_only_store_error)) {
                    isWriteLog = false;
                }
                // 如果仅存储DML并且是查询操作则不进行存储
                String data_log_store_type = MapUtil.getStr(Data.attr, "data_log_store_type");
                if("2".equals(data_log_store_type) && OperationType.SELECT==dataLog.getOperationType()){
                    isWriteLog = false;
                }
            }
        }catch(Throwable e){
            if(isWriteLog) {
                // 设置执行结果、错误原因
                dataLog.setCallResult(CallResult.FAILURE);
                dataLog.setErrorReason(ExceptionUtils.toString(e));
            }
            throw e;
        }finally {
            if(isWriteLog){
                // 设置结束时间、耗时、日志编号
                dataLog.setEndTime(LocalDateTime.now());
                dataLog.setCost((int) Duration.between(dataLog.getStartTime(), dataLog.getEndTime()).toMillis());
                dataLog.setLogId(IdWorker.getId());
        		// 保存操作日志
//				save log
				redisCache.leftPush(Constant.DATA_LOG_KEY, dataLog, RedisCache.NOT_EXPIRE);
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