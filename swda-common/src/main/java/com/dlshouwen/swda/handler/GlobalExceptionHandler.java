package com.dlshouwen.swda.handler;

import com.dlshouwen.swda.exception.SwdaException;
import com.dlshouwen.swda.entity.base.R;
import com.dlshouwen.swda.entity.base.ResultCode;
import com.dlshouwen.swda.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * global exception handler
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * handdler swda exception
	 * @param e
	 * @return R
	 */
	@ExceptionHandler(SwdaException.class)
	@ResponseBody
	public R handlerSwdaException(SwdaException e){
		log.error(ExceptionUtils.toString(e));
		return R.error().code(e.getCode()).message(e.getMessage());
	}
	
	/**
	 * handdler access denied exception
	 * @param e
	 * @return R
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public R handlerAccessDeniedException(AccessDeniedException e){
		log.error(ExceptionUtils.toString(e));
		return R.error().code(ResultCode.PERMISSION_NO_ACCESS).message(e.getMessage());
	}

	/**
	 * handler exception
	 * @param e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R handlerException(Exception e){
		log.error(ExceptionUtils.toString(e));
		return R.error().message(e.getMessage());
	}

}
