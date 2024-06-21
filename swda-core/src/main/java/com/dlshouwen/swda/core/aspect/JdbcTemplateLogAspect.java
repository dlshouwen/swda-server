package com.dlshouwen.swda.core.aspect;

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

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * jdbc template log aspect
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class JdbcTemplateLogAspect {

	/** is write log */
	private boolean isWriteLog = false;
	
	/** data log */
	private DataLog dataLog = new DataLog();

	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * jdbc template log
	 */
	@Pointcut("execution(public * com.dlshouwen.swda.core.dao.BaseDao.*(..))")
	public void jdbcTemplateLog() {
	}

	/**
	 * arround
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "jdbcTemplateLog()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
//		set call type, call result
		dataLog.setCallType(CallType.JDBC_TEMPLATE);
		dataLog.setCallResult(CallResult.SUCCESS);
//		set call source, line no
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for (StackTraceElement element : stack) {
				if (!element.getClassName().startsWith("com.dlshouwen.swda.core.dao.BaseDao") && element.getClassName().startsWith("com.dlshouwen")) {
					dataLog.setCallSource(element.getClassName());
					dataLog.setLineNo(element.getLineNumber());
					break;
				}
			}
		}
//		ignore jdbc template data log
		if (!dataLog.getCallSource().endsWith("DataLoader") && !dataLog.getCallSource().endsWith("JdbcTemplate")) {
			isWriteLog = true;
		} else {
			isWriteLog = false;
		}
		try {
//			if need write
			if (isWriteLog) {
//				set start time
				dataLog.setStartTime(LocalDateTime.now());
			}
//			proceed
			Object result = pjp.proceed();
//			if need write
			if (isWriteLog) {
//				set end time, cost, execute result
				dataLog.setEndTime(LocalDateTime.now());
				dataLog.setCost((int) LocalDateTimeUtil.between(dataLog.getStartTime(), dataLog.getEndTime()).toMillis());
				dataLog.setExecuteResult(JsonUtils.toJsonString(result));
//				if only store error
				String data_log_only_store_error = MapUtil.getStr(Data.attr, "data_log_only_store_error");
				if ("1".equals(data_log_only_store_error)) {
					isWriteLog = false;
				}
//				if only store dml
				String data_log_store_type = MapUtil.getStr(Data.attr, "data_log_store_type");
				if ("2".equals(data_log_store_type) && OperationType.SELECT==dataLog.getOperationType()) {
					isWriteLog = false;
				}
			}
//			return result
			return result;
		} catch (Throwable e) {
//			if need write
			if (isWriteLog) {
//				set end time, cost, call result, error reason
				dataLog.setEndTime(LocalDateTime.now());
				dataLog.setCost((int) Duration.between(dataLog.getStartTime(), dataLog.getEndTime()).toMillis());
				dataLog.setCallResult(CallResult.FAILURE);
				dataLog.setErrorReason(ExceptionUtils.toString(e));
			}
//			log
			log.error("jdbc template error: ", e);
//			throw e
			throw e;
		} finally {
//			if need write
			if (isWriteLog) {
//				set log id
				dataLog.setLogId(IdWorker.getId());
//				save log
				redisCache.leftPush(Constant.DATA_LOG_KEY, dataLog, RedisCache.NOT_EXPIRE);
			}
		}
	}

	/**
	 * before
	 * @param joinPoint
	 */
	@Before(value = "jdbcTemplateLog()")
	public void before(JoinPoint joinPoint) {
//		if need write
		if (isWriteLog) {
//			get request
			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//			set ip
			dataLog.setIp(IpUtils.getIp(request));
//			set operation sql
			dataLog.setOperationSql(joinPoint.getArgs()[0].toString());
//			handler operation sql
			String sql = dataLog.getOperationSql().toLowerCase().trim();
//			set operation type
			dataLog.setOperationType(OperationType.UNKNOWN);
			if (sql.startsWith("select"))
				dataLog.setOperationType(OperationType.SELECT);
			if (sql.startsWith("insert"))
				dataLog.setOperationType(OperationType.INSERT);
			if (sql.startsWith("update"))
				dataLog.setOperationType(OperationType.UPDATE);
			if (sql.startsWith("delete"))
				dataLog.setOperationType(OperationType.DELETE);
//			set params
			List<Object> args = new ArrayList<>();
			for (int i = 1; i < joinPoint.getArgs().length; i++) {
				args.add(joinPoint.getArgs()[i]);
			}
			dataLog.setParams(JsonUtils.toJsonString(args));
//			set execute type, result type
			dataLog.setExecuteType(joinPoint.getSignature().getName());
			dataLog.setResultType(((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType().getName());
//			get user detail
			UserDetail user = SecurityUser.getUser();
//			if user is not null
			if(user!=null) {
//				set tenant id, user id, user name, organ id, organ name
				dataLog.setTenantId(user.getTenantId());
				dataLog.setUserId(user.getUserId());
				dataLog.setUserName(user.getUsername());
				dataLog.setOrganId(user.getOrganId());
				dataLog.setOrganName(user.getOrganName());
			}
		}
	}

}
