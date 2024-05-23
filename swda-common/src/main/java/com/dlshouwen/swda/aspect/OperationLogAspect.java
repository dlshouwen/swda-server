package com.dlshouwen.swda.aspect;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dlshouwen.swda.annotation.Operation;
import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.base.LoginUser;
import com.dlshouwen.swda.entity.base.R;
import com.dlshouwen.swda.entity.base.ResultCode;
import com.dlshouwen.swda.entity.dict.OperationResult;
import com.dlshouwen.swda.entity.dict.OperationType;
import com.dlshouwen.swda.entity.log.OperationLog;
import com.dlshouwen.swda.queue.OperationLogBatchQueue;
import com.dlshouwen.swda.utils.ExceptionUtils;
import com.dlshouwen.swda.utils.IpUtils;
import com.dlshouwen.swda.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * operation log aspect
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Aspect
@Component
public class OperationLogAspect {

	/**
	 * operation log batch queue
	 */
	@Resource
	OperationLogBatchQueue<OperationLog> operationLogBatchQueue;

	/**
	 * pointcut in annotation
	 */
	@Pointcut("@annotation(com.dlshouwen.swda.annotation.Operation)")
	public void operationLogPointCut() {
	}

	/**
	 * pointcut in execution
	 */
	@Pointcut("execution(* com.dlshouwen.swda.*.controller..*.*(..))")
	public void operationExceptionLogPointCut() {
	}

	/**
	 * around
	 * @param pjp
	 */
	@Around(value = "operationLogPointCut()")
	public Object around(ProceedingJoinPoint pjp) {
//		defined operation log
		OperationLog operationLog = new OperationLog();
//		defined is write log
		boolean isWriteLog = true;
//		set response start
		operationLog.setResponseStart(new Date());
//		set operation type / operation detail
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Operation operation = method.getAnnotation(Operation.class);
		if (operation != null) {
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.UNKNOWN) operationLog.setOperationType(OperationType.UNKNOWN);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.SELECT) operationLog.setOperationType(OperationType.SELECT);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.INSERT) operationLog.setOperationType(OperationType.INSERT);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.UPDATE) operationLog.setOperationType(OperationType.UPDATE);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.DELETE) operationLog.setOperationType(OperationType.DELETE);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.LOGIN) operationLog.setOperationType(OperationType.LOGIN);
			if(operation.type()==com.dlshouwen.swda.entity.base.OperationType.LOGOUT) operationLog.setOperationType(OperationType.LOGOUT);
			operationLog.setOperationDetail(operation.description());
		}
//		if only store dml
		if("2".equals(Data.attr.get("operation_log_store_type")) && OperationType.SELECT.equals(operationLog.getOperationType())){
			try {
//				proceed
				return pjp.proceed();
			} catch (Throwable e) {
//				return error
				return R.error().message(ExceptionUtils.toString(e));
			}
		}
		try {
//			proceed
			Object o =  pjp.proceed();
//			if return r
			if(o instanceof R r){
//				get operation result
				String operationResult = OperationResult.RESPONSE_ERROR;
				if(r.getCode() == ResultCode.SUCCESS) operationResult = OperationResult.SUCCESS;
				if(r.getCode() ==ResultCode.TOKEN_INVALID) operationResult = OperationResult.TOKEN_INVALID;
				if(r.getCode() ==ResultCode.PERMISSION_NO_ACCESS) operationResult = OperationResult.NO_LIMIT;
				if(r.getCode() ==ResultCode.AUTHENTICATION_TIMEOUT) operationResult = OperationResult.TOKEN_INVALID;
//				set operation result
				operationLog.setOperationResult(operationResult);
//				set error message
				if(!r.getResult()){
					operationLog.setErrorReason(r.getMessage());
				}
			}else {
//				set operation result
				operationLog.setOperationResult(OperationResult.SUCCESS);
			}
//			if only store error & operation result is success
			if("1".equals(Data.attr.get("operation_log_only_store_error"))&&operationLog.getOperationResult().equals(OperationResult.SUCCESS)) {
				isWriteLog = false;
			}
//			return
			return o;
		} catch (Throwable e) {
//			set operation result / error reason
			operationLog.setOperationResult(String.valueOf(ResultCode.ERROR));
			operationLog.setErrorReason(ExceptionUtils.toString(e));
//			return error
			return R.error().message(e.getMessage());
		} finally {
//			if write log
			if(isWriteLog){
//				set id
				operationLog.setLogId(IdWorker.getId());
//				set call source
				operationLog.setCallSource(pjp.getTarget().getClass().getName()+"."+method.getName());
//				get attributes
				RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
//				if attributes is not null
				if(attributes!=null){
//					get request
					HttpServletRequest request = (HttpServletRequest) attributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//					if request not null
					if(request!=null){
//						set operation url / ip
						operationLog.setOperationUrl(request.getRequestURI());
						operationLog.setIp(IpUtils.getIpAddr(request));
//						set params
						try {
							Map<String, Object> params = convertRequestParamToMap(request.getParameterMap());
							if(pjp.getArgs()!=null) {
								for(int i=0; i<pjp.getArgs().length; i++) {
									Object arg = pjp.getArgs()[i];
									if(arg.getClass().getName().startsWith("javax.")) continue;
									params.put("method_param_"+i, arg);
								}
							}
							operationLog.setParams(new ObjectMapper().writeValueAsString(params));
						}catch(Exception e) {
							operationLog.setParams("resolve error:"+ExceptionUtils.toString(e));
						}
					}
				}
//				defined login user
				LoginUser loginUser = null;
//				if attributes not null
				if(attributes!=null){
//					get request
					HttpServletRequest request = (HttpServletRequest) attributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//					if request not null
					if(request!=null){
//						get token
						String token = request.getHeader("token");
//						get login user
						if(!StringUtils.isEmpty(token)){
							loginUser = TokenUtils.getLoginUser(token);
						}
					}
				}
				operationLog.setUserId(loginUser==null?null:loginUser.getUserId());
				operationLog.setUserName(loginUser==null?null:loginUser.getUserName());
				operationLog.setOrganId(loginUser==null?null:loginUser.getOrganId());
				operationLog.setOrganName(loginUser==null?null:loginUser.getOrganName());
//				set response end / cost
				operationLog.setResponseEnd(new Date());
				operationLog.setCost((int) (operationLog.getResponseEnd().getTime()-operationLog.getResponseStart().getTime()));
//				add to queue
				operationLogBatchQueue.add(operationLog);
			}
		}
	}

	/**
	 * convert request param to map
	 * @param paramMap
	 */
	private Map<String, Object> convertRequestParamToMap(Map<String, String[]> paramMap) {
		Map<String, Object> returnMap = new HashMap<>();
		for (String key : paramMap.keySet()) {
			returnMap.put(key, paramMap.get(key)[0]);
		}
		return returnMap;
	}

}
