package com.dlshouwen.swda.core.base.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.base.enums.ResultCode;
import com.dlshouwen.swda.core.base.exception.SwdaException;

import lombok.extern.slf4j.Slf4j;

/**
 * global exception handler
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * handle swda exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SwdaException.class)
	public R<String> handleSwdaException(SwdaException e) {
		return R.error(e.getCode(), e.getMessage());
	}

	/**
	 * handler bind exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public R<String> handleBindException(BindException e) {
		FieldError fieldError = e.getFieldError();
		assert fieldError != null;
		return R.error(fieldError.getDefaultMessage());
	}

	/**
	 * handle resource not found exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(NoResourceFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: " + e.getResourcePath());
	}

	/**
	 * handle access denied exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public R<String> handleAccessDeniedException(Exception e) {
		return R.error(ResultCode.FORBIDDEN);
	}

	/**
	 * handle exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public R<String> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return R.error(ResultCode.INTERNAL_SERVER_ERROR);
	}

}
