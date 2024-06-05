package com.dlshouwen.swda.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dlshouwen.swda.entity.base.R;
import com.dlshouwen.swda.entity.base.ResultCode;
import com.dlshouwen.swda.exception.SwdaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(SwdaException.class)
    public R<String> handleException(SwdaException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public R<String> bindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        return R.error(fieldError.getDefaultMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: " + e.getResourcePath());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R<String> handleAccessDeniedException(Exception e) {
        return R.error(ResultCode.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(ResultCode.INTERNAL_SERVER_ERROR);
    }

}
