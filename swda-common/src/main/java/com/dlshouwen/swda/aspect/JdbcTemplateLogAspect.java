package com.dlshouwen.swda.aspect;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.base.LoginUser;
import com.dlshouwen.swda.entity.base.R;
import com.dlshouwen.swda.entity.dict.CallResult;
import com.dlshouwen.swda.entity.dict.CallType;
import com.dlshouwen.swda.entity.dict.OperationType;
import com.dlshouwen.swda.entity.log.DataLog;
import com.dlshouwen.swda.queue.DataLogBatchQueue;
import com.dlshouwen.swda.utils.ExceptionUtils;
import com.dlshouwen.swda.utils.IpUtils;
import com.dlshouwen.swda.utils.TokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

/**
 * jdbc template log aspect
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Aspect
@Component
public class JdbcTemplateLogAspect {

	/**
	 * data log batch queue
	 */
	@Resource
	DataLogBatchQueue<DataLog> dataLogBatchQueue;

	/**
	 * construct
	 * <p>match com.dlshouwen.swda.core.base.dao.BaseDao methods</p>
	 */
	@Pointcut("execution(public * com.dlshouwen.swda.dao.BaseDao.*(..))")
	public void jdbcTemplateLog(){
	}

	/**
	 * around
	 * @param pjp
	 */
	@Around(value = "jdbcTemplateLog()")
	public Object around(ProceedingJoinPoint pjp) {
//		defined call source / line no
		String callSource = "";
		int lineNo = 0;
//		get call source / line no
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for(int i=1; i<stack.length; i++){
				String _callSource = stack[i].getClassName();
				int _lineNo = stack[i].getLineNumber();
				if (!_callSource.startsWith("com.dlshouwen.swda.core.base.dao.BaseDao")&&
						!_callSource.startsWith("com.dlshouwen.swda.core.base.utils.PagerUtils")&&
						_callSource.startsWith("com.dlshouwen")) {
					callSource = _callSource;
					lineNo = _lineNo;
					break;
				}
			}
		}
//		some call source not write
		if(StrUtil.isEmpty(callSource)||
				callSource.endsWith("DataLoader")||callSource.endsWith("DataConfig")||
				callSource.endsWith("DataLogBatchQueue")||callSource.endsWith("OperationLogBatchQueue")||
				callSource.endsWith("JdbcTemplate")||callSource.endsWith("LogUtils")){
			try {
//				proceed
				return pjp.proceed();
			} catch (Throwable e) {
//				return error
				return R.error().message(ExceptionUtils.toString(e));
			}
		}
//		get sql
		String sql = pjp.getArgs()[0].toString().toLowerCase().trim();
//		get operation type
		String operationType = OperationType.UNKNOWN;
		if(sql.startsWith("select")) operationType=OperationType.SELECT;
		if(sql.startsWith("insert")) operationType=OperationType.INSERT;
		if(sql.startsWith("update")) operationType=OperationType.UPDATE;
		if(sql.startsWith("delete")) operationType=OperationType.DELETE;
//		defined data log
		DataLog dataLog = new DataLog();
//		defined is write log
		boolean isWriteLog = true;
//		set start time
		dataLog.setStartTime(new Date());
		try {
//			proceed
			Object o =  pjp.proceed();
//			if only store error
			if("1".equals(Data.attr.get("data_log_only_store_error"))) {
				isWriteLog = false;
			}
//			if only dml & operation type is select
			if("2".equals(Data.attr.get("data_log_store_type")) && OperationType.SELECT.equals(operationType)){
				isWriteLog = false;
			}
//			if write
			if(isWriteLog) {
//				set call result
				dataLog.setCallResult(CallResult.SUCCESS);
//				set execute result
				dataLog.setExecuteResult(new ObjectMapper().writeValueAsString(o));
			}
//			return
			return o;
		} catch (Throwable e) {
//			set call result / error reason
			dataLog.setCallResult(CallResult.FAILURE);
			dataLog.setErrorReason(ExceptionUtils.toString(e));
//			return error
			return R.error().message(ExceptionUtils.toString(e));
		} finally {
//			if write log
			if(isWriteLog){
//				set log id / call source / line no / call type
				dataLog.setLogId(IdWorker.getId());
				dataLog.setCallSource(callSource);
				dataLog.setLineNo(lineNo);
				dataLog.setCallType(CallType.JDBC_TEMPLATE);
//				get attributes
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//				if attributes not null
				if(attributes!=null){
//					get request
					HttpServletRequest request = attributes.getRequest();
//					set ip
					dataLog.setIp(IpUtils.getIpAddr(request));
				}
//				set operation sql
				dataLog.setOperationSql(pjp.getArgs()[0].toString());
//				set operation type
				dataLog.setOperationType(operationType);
//				set params
				List<Object> args = new ArrayList<>();
				for(int i=0; i<pjp.getArgs().length; i++){
					if(i==0) continue;
					args.add(pjp.getArgs()[i]);
				}
				try{
					dataLog.setParams(new ObjectMapper().writeValueAsString(args));
				}catch(JsonProcessingException e){
					dataLog.setParams("no param");
				}
//				set execute type / result type
				dataLog.setExecuteType(pjp.getSignature().getName());
				dataLog.setResultType(((MethodSignature)pjp.getSignature()).getMethod().getReturnType().getName());
//				defined login user
				LoginUser loginUser = null;
//				if attributes not null
				if(attributes!=null){
//					get request
					HttpServletRequest request = attributes.getRequest();
//					get token
					String token = request.getHeader("token");
//					get login user
					if(!StringUtils.isEmpty(token)){
						loginUser = TokenUtils.getLoginUser(token);
					}
				}
//				set user / organ
				dataLog.setUserId(loginUser==null?null:loginUser.getUserId());
				dataLog.setUserName(loginUser==null?null:loginUser.getUserName());
				dataLog.setOrganId(loginUser==null?null:loginUser.getOrganId());
				dataLog.setOrganName(loginUser==null?null:loginUser.getOrganName());
//				set end time / cost
				dataLog.setEndTime(new Date());
				dataLog.setCost((int)(dataLog.getEndTime().getTime()-dataLog.getStartTime().getTime()));
//				add to queue
				dataLogBatchQueue.add(dataLog);
			}
		}
	}

}
