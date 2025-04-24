package com.dlshouwen.swda.core.log.aspect;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dlshouwen.swda.core.base.cache.RedisCache;
import com.dlshouwen.swda.core.base.constant.Constant;
import com.dlshouwen.swda.core.base.utils.ExceptionUtils;
import com.dlshouwen.swda.core.base.utils.HttpContextUtils;
import com.dlshouwen.swda.core.base.utils.IpUtils;
import com.dlshouwen.swda.core.base.utils.JsonUtils;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.dict.CallResult;
import com.dlshouwen.swda.core.log.dto.OperationLogDTO;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * operation log aspect
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Aspect
@Component
@AllArgsConstructor
public class OperationLogAspect {

	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * arround
	 * @param joinPoint
	 * @param operation
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(operation)")
	public Object around(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {
//		defined start time
		LocalDateTime startTime = LocalDateTime.now();
//		try all exception
		try {
//			proceed
			Object result = joinPoint.proceed();
//			save operation log
			saveOperationLog(joinPoint, operation, startTime, CallResult.SUCCESS, JsonUtils.toJsonString(result));
//			return result
			return result;
		} catch (Exception e) {
//			save operation log
			saveOperationLog(joinPoint, operation, startTime, CallResult.FAILURE, ExceptionUtils.toString(e));
//			throw e
			throw e;
		}
	}

	/**
	 * save operation log
	 * @param joinPoint
	 * @param operation
	 * @param startTime
	 * @param callResult
	 * @param result
	 * @param status
	 */
	private void saveOperationLog(ProceedingJoinPoint joinPoint, Operation operation, LocalDateTime startTime,
			String callResult, String result) {
//		defined operation log
		OperationLogDTO operationLog = new OperationLogDTO();
//		set start time, end time, cost
		operationLog.setStartTime(startTime);
		operationLog.setEndTime(LocalDateTime.now());
		operationLog.setCost((int) LocalDateTimeUtil.between(startTime, operationLog.getEndTime()).toMillis());
//      get method
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
//		set call source
		operationLog.setCallSource(joinPoint.getTarget().getClass().getName() + "." + method.getName());
//		set operation type
		operationLog.setOperationType(operation.type()[0].getValue());
//		set call source, line no
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		if (stack != null) {
			for (StackTraceElement element : stack) {
				if (element.getClassName().startsWith("com.dlshouwen")) {
					operationLog.setCallSource(element.getClassName());
					operationLog.setLineNo(element.getLineNumber());
					break;
				}
			}
		}
//		get user
		UserDetail user = SecurityUser.getUser();
//		if user is not null
		if (user != null) {
//			set tenant id, user id, user name, organ id, organ name
			operationLog.setTenantId(user.getTenantId());
			operationLog.setUserId(user.getUserId());
			operationLog.setRealName(user.getRealName());
			operationLog.setOrganId(user.getOrganId());
			operationLog.setOrganName(user.getOrganName());
		}
//		set operation name, operation module, operation type
		operationLog.setOperationName(operation.name());
		operationLog.setOperationModule(operation.module());
		operationLog.setOperationType(operation.type()[0].getValue());
//		if no module
		if (StrUtil.isBlank(operationLog.getOperationModule())) {
//			get swagger tag
			Tag tag = ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass()
					.getAnnotation(Tag.class);
//			if tag not null then set module
			if (tag != null) {
				operationLog.setOperationModule(tag.name());
			}
		}
//		if no name
		if (StrUtil.isBlank(operationLog.getOperationName())) {
//			get swagger tag
			io.swagger.v3.oas.annotations.Operation _operation = ((MethodSignature) joinPoint.getSignature())
					.getMethod().getAnnotation(io.swagger.v3.oas.annotations.Operation.class);
			if (_operation != null) {
				operationLog.setOperationName(_operation.summary());
			}
		}
//		get request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		if has request
		if (request != null) {
//			set ip, user agent, request url, request method
			operationLog.setIp(IpUtils.getIp(request));
			operationLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
			operationLog.setRequestUri(request.getRequestURI());
			operationLog.setRequestMethod(request.getMethod());
		}
//		set request params, call result
		operationLog.setRequestParams(getMethodParams(joinPoint));
		operationLog.setCallResult(callResult);
//		if success
		if(callResult == CallResult.SUCCESS) {
//			set response result
			operationLog.setResponseResult(result);
		}else {
//			set response result
			operationLog.setResponseResult("[error]");
//			set error reason
			operationLog.setErrorReason(result);
		}
//		save log
		redisCache.leftPush(Constant.OPERATION_LOG_KEY, operationLog, RedisCache.NOT_EXPIRE);
	}

	/**
	 * get method params
	 * @param joinPoint
	 * @return method params
	 */
	private String getMethodParams(ProceedingJoinPoint joinPoint) {
//		get method names, values
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String[] names = methodSignature.getParameterNames();
		Object[] values = joinPoint.getArgs();
//		defined params
		Map<String, Object> params = MapUtil.newHashMap(values.length);
//		for each names
		for (int i = 0; i < names.length; i++) {
//			put params
			params.put(names[i], ignoreParams(values[i]) ? "[ignore]" : values[i]);
		}
//		return params json string
		return JsonUtils.toJsonString(params);
	}

	/**
	 * ignore params
	 * @param object
	 * @return is ignore
	 */
	private static boolean ignoreParams(Object object) {
//		null
		if(object==null) {
//			return
			return false;
		}
//		get class
		Class<?> clazz = object.getClass();
//		handle array
		if (clazz.isArray()) {
			return IntStream.range(0, Array.getLength(object))
					.anyMatch(index -> ignoreParams(Array.get(object, index)));
		}
//		handle collection
		if (Collection.class.isAssignableFrom(clazz)) {
			return ((Collection<?>) object).stream().anyMatch((Predicate<Object>) OperationLogAspect::ignoreParams);
		}
//		handle map
		if (Map.class.isAssignableFrom(clazz)) {
			return ignoreParams(((Map<?, ?>) object).values());
		}
//		ignore: mutipart file, request, response, bind result, model, model and view 
		return object instanceof MultipartFile || object instanceof HttpServletRequest
				|| object instanceof HttpServletResponse || object instanceof BindingResult || object instanceof Model
				|| object instanceof ModelAndView;
	}

}