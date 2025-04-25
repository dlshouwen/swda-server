package com.dlshouwen.swda.core.log.aspect;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.core.base.cache.RedisCache;
import com.dlshouwen.swda.core.base.constant.Constant;
import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.base.utils.ExceptionUtils;
import com.dlshouwen.swda.core.base.utils.HttpContextUtils;
import com.dlshouwen.swda.core.base.utils.IpUtils;
import com.dlshouwen.swda.core.base.utils.JsonUtils;
import com.dlshouwen.swda.core.log.dict.CallResult;
import com.dlshouwen.swda.core.log.dict.CallType;
import com.dlshouwen.swda.core.log.dict.ExecuteType;
import com.dlshouwen.swda.core.log.dict.OperationType;
import com.dlshouwen.swda.core.log.dto.DataLogDTO;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * jdbc template log aspect
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Component
public class JdbcTemplateLogAspect {

	/** is write log */
	private boolean isWriteLog = false;
	
	/** data log */
	private DataLogDTO dataLog = new DataLogDTO();

	/** redis cache */
	@Autowired
	private RedisCache redisCache;

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
//				set execute result: list
				if(result instanceof List) {
					dataLog.setExecuteResult("size: "+((List<?>)result).size());
				}else {
					dataLog.setExecuteResult(JsonUtils.toJsonString(result));
				}
//				if only store error
				String data_log_only_store_error = MapUtil.getStr(Data.attr, "data_log_only_store_error");
				if ("1".equals(data_log_only_store_error)) {
					isWriteLog = false;
				}
//				if only store dml
				String data_log_store_type = MapUtil.getStr(Data.attr, "data_log_store_type");
				if ("2".equals(data_log_store_type) && OperationType.SELECT==dataLog.getExecuteType()) {
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
//				set data log id
				dataLog.setDataLogId(IdWorker.getId());
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
//			set execute sql
			dataLog.setExecuteSql(joinPoint.getArgs()[0].toString());
//			handler execute sql
			String sql = dataLog.getExecuteSql().toLowerCase().trim();
//			set execute type
			dataLog.setExecuteType(ExecuteType.UNKNOWN);
			if (sql.startsWith("select"))
				dataLog.setExecuteType(ExecuteType.SELECT);
			if (sql.startsWith("insert"))
				dataLog.setExecuteType(ExecuteType.INSERT);
			if (sql.startsWith("update"))
				dataLog.setExecuteType(ExecuteType.UPDATE);
			if (sql.startsWith("delete"))
				dataLog.setExecuteType(ExecuteType.DELETE);
//			set params
			List<Object> args = new ArrayList<>();
			for (int i = 1; i < joinPoint.getArgs().length; i++) {
				args.add(joinPoint.getArgs()[i]);
			}
			dataLog.setExecuteParams(JsonUtils.toJsonString(args));
//			set execute method, execute result class
			dataLog.setExecuteMethod(joinPoint.getSignature().getName());
			dataLog.setExecuteResultClass(((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType().getName());
//			get user detail
			UserDetail user = SecurityUser.getUser();
//			if user is not null
			if(user!=null) {
//				set tenant id, user id, user name, organ id, organ name
				dataLog.setTenantId(user.getTenantId());
				dataLog.setUserId(user.getUserId());
				dataLog.setRealName(user.getRealName());
				dataLog.setOrganId(user.getOrganId());
				dataLog.setOrganName(user.getOrganName());
			}
		}
	}

}
